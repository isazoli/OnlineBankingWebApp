package com.isazoli.onlinebanking.transfermoney;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.isazoli.onlinebanking.account.BankAccount;
import com.isazoli.onlinebanking.account.BankAccountRepository;
import com.isazoli.onlinebanking.account.TargetAccountRepository;
import com.isazoli.onlinebanking.support.web.Message;
import com.isazoli.onlinebanking.support.web.Message.Type;

/**
 * Handles transfer money confirmation requests.
 */
@Controller
@Secured("ROLE_USER")
public class TransferMoneyConfirmationController {
	/**
	 * Reference to source Bank Account repository.
	 */
	private BankAccountRepository bankAccountRepository;
	/**
	 * Reference to target Bank Account repository.
	 */
	private TargetAccountRepository targetAccountRepository;

	@Autowired
	public TransferMoneyConfirmationController(
			BankAccountRepository bankAccountRepository,
			TargetAccountRepository targetAccountRepository) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.targetAccountRepository = targetAccountRepository;
	}

	@RequestMapping(value = "/transferConfirm", method = RequestMethod.POST)
	public ModelAndView transferMoneyConfirm(
			@ModelAttribute(value = "confirmRequest") TransferMoneyRequest transferRequest,
			final RedirectAttributes redirectAttributes) {
		final ModelAndView mv = new ModelAndView("transfermoney/success");
		final BankAccount sourceAccount = bankAccountRepository.findById(transferRequest.getSourceAccountId());
		final String amountStr = transferRequest.getAmount();
		try {
			final Long amount = Long.valueOf(amountStr);
			// validation error: amount on the source account is not enough
			if (sourceAccount.getBalance() < amount)
				return error("transfer.money.account.not.enough.cash", redirectAttributes, transferRequest);
			final BankAccount targetAccount = targetAccountRepository.findById(transferRequest.getTargetAccountId());
			// perform
//			sourceAccount.setBalance(sourceAccount.getBalance() - amount);
//			targetAccount.setBalance(sourceAccount.getBalance() + amount);
//			bankAccountRepository.save(sourceAccount);
//			targetAccountRepository.save(targetAccount);
		} catch (NumberFormatException e) {
			// Cannot happen: amount has been already validated
//		} catch (Exception e) {
//			return error()
		}
		return mv;
	}

	/**
	 * Adds no-argument error message & redirects to the transfer money page.
	 * 
	 * @param message message code.
	 * @param redirectAttributes needed for redirection.
	 * @param transferRequest input model object. 
	 * @return error message added and redirection. 
	 */
	private ModelAndView error(String message,
			RedirectAttributes redirectAttributes,
			TransferMoneyRequest transferRequest) {
		ModelAndView mv = new ModelAndView("transfermoney/confirmation");
		mv.addObject("acc", bankAccountRepository.findById(transferRequest.getSourceAccountId()));
		mv.addObject("targetAcc", targetAccountRepository.findById(transferRequest.getTargetAccountId()));
		mv.addObject("amount", transferRequest.getAmount());
		mv.addObject("message", new Message(message, Type.DANGER));
		return mv;
	}
}
