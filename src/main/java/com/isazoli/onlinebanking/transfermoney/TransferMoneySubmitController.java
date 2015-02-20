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
import com.isazoli.onlinebanking.support.web.MessageHelper;

/**
 * Handles transfer money form post requests.
 */
@Controller
@Secured("ROLE_USER")
public class TransferMoneySubmitController {
	/**
	 * Reference to source Bank Account repository.
	 */
	private BankAccountRepository bankAccountRepository;
	/**
	 * Reference to target Bank Account repository.
	 */
	private TargetAccountRepository targetAccountRepository;

	@Autowired
	public TransferMoneySubmitController(
			BankAccountRepository bankAccountRepository,
			TargetAccountRepository targetAccountRepository) {
		super();
		this.bankAccountRepository = bankAccountRepository;
		this.targetAccountRepository = targetAccountRepository;
	}

	/**
	 * Validates & submits a transfer money request.
	 * 
	 * @param transferRequest model including
	 * @param redirectAttributes needed for validation error redirection.
	 * @return on any validation error redirects to the form, otherwise forwards to the confirmation page.
	 */
	@RequestMapping(value = "/transferSubmit", method = RequestMethod.POST)
	public ModelAndView transferMoneySubmit(
			@ModelAttribute(value = "transferRequest") TransferMoneyRequest transferRequest,
			final RedirectAttributes redirectAttributes) {
		final ModelAndView mv = new ModelAndView("transfermoney/confirmation", "confirmRequest", new TransferMoneyRequest());
		final BankAccount sourceAccount = bankAccountRepository.findById(transferRequest.getSourceAccountId());
		mv.addObject("acc", sourceAccount);
		final BankAccount targetAccount = targetAccountRepository.findById(transferRequest.getTargetAccountId());
		mv.addObject("targetAcc", targetAccount);
		// validation error: source and target accounts cannot be the same
		if (sourceAccount.getId().equals(targetAccount.getId()))
			return error("transfer.money.account.same", transferRequest, redirectAttributes);
		// validation error: source and target accounts are in a different currency
		if (!sourceAccount.getCurrency().equals(targetAccount.getCurrency()))
			return error("transfer.money.account.not.same.currency", transferRequest, redirectAttributes);
		final String amountStr = transferRequest.getAmount();
		try {
			final Long amount = Long.valueOf(amountStr);
			// validation error: not positive
			if (amount <= 0)
				return error("transfer.money.amount.not.positive", transferRequest, redirectAttributes);
			mv.addObject("amount", amountStr);
		} catch (NumberFormatException e) {
			// validation error: not a number
			return error("transfer.money.amount.not.number", transferRequest, redirectAttributes);
		}
		return mv;
	}

	/**
	 * Adds no-argument error message & redirects to the transfer money page.
	 * 
	 * @param message message code.
	 * @param transferRequest input model object. 
	 * @param redirectAttributes needed for redirection.
	 * @return error message added and redirection. 
	 */
	private ModelAndView error(String message,
			TransferMoneyRequest transferRequest,
			RedirectAttributes redirectAttributes) {
		MessageHelper.addErrorAttribute(redirectAttributes, message, new Object[] {});
		ModelAndView mv = new ModelAndView("redirect:/transfermoney");
//		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sourceA", bindingResult.get);
//		redirectAttributes.addFlashAttribute("sourceAccountId", transferRequest.getSourceAccountId());
//		redirectAttributes.addFlashAttribute("sourceAccountId", transferRequest.getSourceAccountId());
//		redirectAttributes.addFlashAttribute("targetAccountId", transferRequest.getTargetAccountId());
//		redirectAttributes.addFlashAttribute("amount", transferRequest.getAmount());
//		ModelAndView mv = new ModelAndView("transfermoney/transferMoney","transferRequest", new TransferMoneyRequest());
//		mv.addObject("bankaccounts", userRepository.getAccounts(principal.getName()));
//		mv.addObject("targetaccounts", targetAccountRepository.getAccounts());
//		mv.addObject("sourceAccountId", transferRequest.getSourceAccountId());
//		mv.addObject("targetAccountId", transferRequest.getTargetAccountId());
//		mv.addObject("amount", transferRequest.getAmount());
//		mv.addObject("message", new Message(message, Type.DANGER));
		return mv;
	}
}
