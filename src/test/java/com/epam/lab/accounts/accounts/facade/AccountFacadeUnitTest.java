package com.epam.lab.accounts.accounts.facade;

import com.epam.lab.accounts.accounts.converter.AccountConverter;
import com.epam.lab.accounts.accounts.dto.AccountDTO;
import com.epam.lab.accounts.accounts.dto.UserDTO;
import com.epam.lab.accounts.accounts.model.requests.CreateUpdateAccountRequest;
import com.epam.lab.accounts.accounts.model.requests.UserRegistrationRequest;
import com.epam.lab.accounts.accounts.service.AccountService;
import com.epam.lab.accounts.accounts.service.ErrorsService;
import com.epam.lab.accounts.accounts.service.SessionService;
import com.epam.lab.accounts.accounts.service.UserService;
import com.epam.lab.accounts.accounts.validator.CreateAccountRequestRequestValidator;
import com.epam.lab.accounts.accounts.validator.UserLoginRequestRequestValidator;
import com.epam.lab.accounts.accounts.validator.UserRegistrationRequestRequestValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountFacadeUnitTest {

    @Mock
    private UserService userService;
    @Mock
    private SessionService sessionService;
    @Mock
    private AccountConverter accountConverter;
    @Mock
    private AccountService accountService;
    @Mock
    private CreateAccountRequestRequestValidator createAccountRequestValidator;


    //public static final String ACCOUNT_SHORT_NAME = "acc";


    private ErrorsService errorsService = new ErrorsService();
    private AccountFacade accountFacade = new AccountFacade();
    private MockHttpSession httpSession = new MockHttpSession();
    private UserLoginRequestRequestValidator userLoginRequestRequestValidator = new UserLoginRequestRequestValidator();
    private UserRegistrationRequestRequestValidator userRegistrationRequestRequestValidator = new UserRegistrationRequestRequestValidator();

    @Before
    public void resetSession() {
        setField(accountFacade, "userService", userService);
        setField(accountFacade, "accountService", accountService);
        setField(accountFacade, "accountConverter", accountConverter);
        setField(accountFacade, "sessionService", sessionService);

        setField(errorsService, "sessionService", sessionService);
        setField(userLoginRequestRequestValidator, "userService", userService);
        setField(userRegistrationRequestRequestValidator, "userService", userService);
        setField(userLoginRequestRequestValidator, "errorsService", errorsService);
        setField(userRegistrationRequestRequestValidator, "errorsService", errorsService);
        setField(sessionService, "session", httpSession);
    }

    @Test
    public void testHandleCreateOrUpdateAccountRequest() {
        // GIVEN
        final CreateUpdateAccountRequest createUpdateAccountRequest = getDefaultUpdateAccountRequest();
        final AccountDTO accountDTO = getAccountDtoForRequest(createUpdateAccountRequest);
        // WHEN
        accountFacade.handleCreateOrUpdateAccountRequest(createUpdateAccountRequest);
        if (isAccountExistsForCode(accountDTO.getCode())) {
            if (isAccountDiffers(accountDTO)) {
                // THEN
                verify(accountService).updateAccount(accountDTO);
            }
        } else {
            // THEN
            verify(createAccountRequestValidator).validate(createUpdateAccountRequest);
            verify(accountService).createAccountForCurrentUser(accountDTO);
        }
//        when(isAccountExistsForCode(accountDTO.getCode().thenReturn(true);
    }

    public boolean isAccountExistsForCode(final String accountCode) {
        return accountService.isAccountExistsForAccountCode(accountCode);
    }

    private boolean isAccountDiffers(AccountDTO accountDTO) {
        return !accountService.getAccountForAccountCode(accountDTO.getCode()).equals(accountDTO);
    }

    private AccountDTO getAccountDtoForRequest(CreateUpdateAccountRequest createUpdateAccountRequest) {
        final AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(createUpdateAccountRequest.getAccountName());
        accountDTO.setCode(createUpdateAccountRequest.getAccountCode());
        accountDTO.setImg(createUpdateAccountRequest.getAccountImage());
        accountDTO.setBalance(createUpdateAccountRequest.getAccountBalance());
        return accountDTO;
    }

    public CreateUpdateAccountRequest getDefaultUpdateAccountRequest() {
        final CreateUpdateAccountRequest createUpdateAccountRequest = new CreateUpdateAccountRequest();
        createUpdateAccountRequest.setAccountCode("Acer-Aspire");
        createUpdateAccountRequest.setAccountName("Acer-Aspire-Department");
        createUpdateAccountRequest.setAccountImage("Acer");
        createUpdateAccountRequest.setAccountBalance(BigDecimal.valueOf(120));
        return createUpdateAccountRequest;
    }
}
