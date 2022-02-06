import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LoginProfileComponentsPage, LoginProfileDeleteDialog, LoginProfileUpdatePage } from './login-profile.page-object';

const expect = chai.expect;

describe('LoginProfile e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let loginProfileComponentsPage: LoginProfileComponentsPage;
  let loginProfileUpdatePage: LoginProfileUpdatePage;
  let loginProfileDeleteDialog: LoginProfileDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LoginProfiles', async () => {
    await navBarPage.goToEntity('login-profile');
    loginProfileComponentsPage = new LoginProfileComponentsPage();
    await browser.wait(ec.visibilityOf(loginProfileComponentsPage.title), 5000);
    expect(await loginProfileComponentsPage.getTitle()).to.eq('cloudfenApp.loginProfile.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(loginProfileComponentsPage.entities), ec.visibilityOf(loginProfileComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LoginProfile page', async () => {
    await loginProfileComponentsPage.clickOnCreateButton();
    loginProfileUpdatePage = new LoginProfileUpdatePage();
    expect(await loginProfileUpdatePage.getPageTitle()).to.eq('cloudfenApp.loginProfile.home.createOrEditLabel');
    await loginProfileUpdatePage.cancel();
  });

  it('should create and save LoginProfiles', async () => {
    const nbButtonsBeforeCreate = await loginProfileComponentsPage.countDeleteButtons();

    await loginProfileComponentsPage.clickOnCreateButton();

    await promise.all([
      loginProfileUpdatePage.setUserNameInput('userName'),
      loginProfileUpdatePage.setUserIdInput('userId'),
      loginProfileUpdatePage.setMemberIdInput('memberId'),
      loginProfileUpdatePage.setPhoneNumberInput('phoneNumber'),
      loginProfileUpdatePage.setEmailIdInput('emailId'),
      loginProfileUpdatePage.setPasswordInput('password'),
      loginProfileUpdatePage.setStatusInput('status'),
      loginProfileUpdatePage.setActivationCodeInput('activationCode'),
    ]);

    await loginProfileUpdatePage.save();
    expect(await loginProfileUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await loginProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LoginProfile', async () => {
    const nbButtonsBeforeDelete = await loginProfileComponentsPage.countDeleteButtons();
    await loginProfileComponentsPage.clickOnLastDeleteButton();

    loginProfileDeleteDialog = new LoginProfileDeleteDialog();
    expect(await loginProfileDeleteDialog.getDialogTitle()).to.eq('cloudfenApp.loginProfile.delete.question');
    await loginProfileDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(loginProfileComponentsPage.title), 5000);

    expect(await loginProfileComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
