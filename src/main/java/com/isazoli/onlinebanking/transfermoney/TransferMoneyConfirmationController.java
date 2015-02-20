package com.isazoli.onlinebanking.transfermoney;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.isazoli.onlinebanking.account.BankAccount;
import com.isazoli.onlinebanking.account.BankAccountRepository;
import com.isazoli.onlinebanking.account.TargetAccountRepository;
import com.isazoli.onlinebanking.account.TransferMoneyService;
import com.isazoli.onlinebanking.support.web.Message;
import com.isazoli.onlinebanking.support.web.Message.Type;

/**
 * Handles transfer money confirmation requests.
 */
@Controller
@Secured("ROLE_USER")
public class TransferMoneyConfirmationController {
	/**
	 * Logger.
	 */
	private static final Logger log = LoggerFactory.getLogger(TransferMoneyConfirmationController.class);
	/**
	 * Reference to source Bank Account repository.
	 */
	private BankAccountRepository bankAccountRepository;
	/**
	 * Reference to target Bank Account repository.
	 */
	private TargetAccountRepository targetAccountRepository;
	/**
	 * Transfer money service.
	 */
	private TransferMoneyService transferMoneyService;

	@Autowired
	public TransferMoneyConfirmationController(
			BankAccountRepository bankAccountRepository,
			TargetAccountRepository targetAccountRepository,
			TransferMoneyService transferMoneyService) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.targetAccountRepository = targetAccountRepository;
		this.transferMoneyService = transferMoneyService;
	}
	
	/**
	 * Performs money transfer between a source and a target account.
	 * 
	 * @param transferRequest request including source and target account details.
	 * @param redirectAttributes redirection 
	 * @return
	 */
	@RequestMapping(value = "/transferConfirm", method = RequestMethod.POST)
	public ModelAndView transferMoneyConfirm(@ModelAttribute(value = "confirmRequest") TransferMoneyRequest transferRequest) {
		final ModelAndView mv = new ModelAndView("transfermoney/success");
		final BankAccount sourceAccount = bankAccountRepository.findById(transferRequest.getSourceAccountId());
		final String amountStr = transferRequest.getAmount();
		try {
			final Long amount = Long.valueOf(amountStr);
			// validation error: amount on the source account is not enough
			if (sourceAccount.getBalance() < amount)
				return error("transfer.money.account.not.enough.cash", transferRequest);
			final BankAccount targetAccount = targetAccountRepository.findById(transferRequest.getTargetAccountId());
			transferMoneyService.transferMoney(sourceAccount, targetAccount, amount);
		} catch (NumberFormatException e) {
			// Cannot happen: amount has been already validated
		} catch (Exception e) {
			log.error("Unexpected error occured during money transfer", e);
			return error("transfer.money.unexpected.error", transferRequest);
		}
		return mv;
	}

	/**
	 * Adds no-argument error message & redirects back to the confirmation page.
	 * 
	 * @param message message code.
	 * @param transferRequest input model object. 
	 * @return error message added and redirection. 
	 */
	private ModelAndView error(String message,
			TransferMoneyRequest transferRequest) {
		ModelAndView mv = new ModelAndView("transfermoney/confirmation");
		mv.addObject("acc", bankAccountRepository.findById(transferRequest.getSourceAccountId()));
		mv.addObject("targetAcc", targetAccountRepository.findById(transferRequest.getTargetAccountId()));
		mv.addObject("amount", transferRequest.getAmount());
		mv.addObject("message", new Message(message, Type.DANGER));
		return mv;
	}
}
