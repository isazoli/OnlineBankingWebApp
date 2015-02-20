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
	@RequestMapping(value = "/transfer2", method = RequestMethod.POST)
	public ModelAndView transferMoneySubmit(
			@ModelAttribute(value = "transferRequest") TransferMoneyRequest transferRequest,
			final RedirectAttributes redirectAttributes) {
		final ModelAndView mv = new ModelAndView("transfermoney/confirmation");
		final BankAccount sourceAccount = bankAccountRepository.findById(transferRequest.getSourceAccountId());
		mv.addObject("acc", sourceAccount);
		final BankAccount targetAccount = targetAccountRepository.findById(transferRequest.getTargetAccountId());
		mv.addObject("targetAcc", targetAccount);
		// validation error: source and target accounts cannot be the same
		if (sourceAccount.getId().equals(targetAccount.getId()))
			return error("transfer.money.account.same", redirectAttributes, transferRequest);
		// validation error: source and target accounts are in a different currency
		if (!sourceAccount.getCurrency().equals(targetAccount.getCurrency()))
			return error("transfer.money.account.not.same.currency", redirectAttributes, transferRequest);
		final String amountStr = transferRequest.getAmount();
		try {
			final Long amount = Long.valueOf(amountStr);
			// validation error: not positive
			if (amount <= 0)
				return error("transfer.money.amount.not.positive", redirectAttributes, transferRequest);
			mv.addObject("amount", amountStr);
		} catch (NumberFormatException e) {
			// validation error: not a number
			return error("transfer.money.amount.not.number", redirectAttributes, transferRequest);
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
		MessageHelper.addErrorAttribute(redirectAttributes, message, new Object[] {});
		ModelAndView mv = new ModelAndView("redirect:transfermoney");
		// new ModelAndView("redirect:transfermoney", "transferRequest", new
		// TransferMoneyRequest());
		// mv.addObject("transferRequest", transferRequest);
		return mv;
	}
}
