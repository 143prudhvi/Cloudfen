import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { H1BComponentsPage, H1BDeleteDialog, H1BUpdatePage } from './h-1-b.page-object';

const expect = chai.expect;

describe('H1B e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let h1BComponentsPage: H1BComponentsPage;
  let h1BUpdatePage: H1BUpdatePage;
  let h1BDeleteDialog: H1BDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load H1BS', async () => {
    await navBarPage.goToEntity('h-1-b');
    h1BComponentsPage = new H1BComponentsPage();
    await browser.wait(ec.visibilityOf(h1BComponentsPage.title), 5000);
    expect(await h1BComponentsPage.getTitle()).to.eq('cloudfenApp.h1B.home.title');
    await browser.wait(ec.or(ec.visibilityOf(h1BComponentsPage.entities), ec.visibilityOf(h1BComponentsPage.noResult)), 1000);
  });

  it('should load create H1B page', async () => {
    await h1BComponentsPage.clickOnCreateButton();
    h1BUpdatePage = new H1BUpdatePage();
    expect(await h1BUpdatePage.getPageTitle()).to.eq('cloudfenApp.h1B.home.createOrEditLabel');
    await h1BUpdatePage.cancel();
  });

  it('should create and save H1BS', async () => {
    const nbButtonsBeforeCreate = await h1BComponentsPage.countDeleteButtons();

    await h1BComponentsPage.clickOnCreateButton();

    await promise.all([
      h1BUpdatePage.setUserIdInput('userId'),
      h1BUpdatePage.setFirstNameInput('firstName'),
      h1BUpdatePage.setMiddleNameInput('middleName'),
      h1BUpdatePage.setLastNameInput('lastName'),
      h1BUpdatePage.setDateOfBirthInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      h1BUpdatePage.setCountryOfBirthInput('countryOfBirth'),
      h1BUpdatePage.setCountryOfCitizenShipInput('countryOfCitizenShip'),
      h1BUpdatePage.setPassportNumberInput('passportNumber'),
      h1BUpdatePage.genderSelectLastOption(),
      h1BUpdatePage.categorySelectLastOption(),
      h1BUpdatePage.setEmailInput('email'),
      h1BUpdatePage.setCurrentAddressInput('currentAddress'),
      h1BUpdatePage.setPhoneNumberInput('phoneNumber'),
      h1BUpdatePage.setBachelorDegreeInput('bachelorDegree'),
      h1BUpdatePage.setYearInput('year'),
      h1BUpdatePage.setCurrentVisaStatusInput('currentVisaStatus'),
      h1BUpdatePage.setReferedByInput('referedBy'),
      h1BUpdatePage.setPassportFrontPageInput('passportFrontPage'),
      h1BUpdatePage.setPassportBackPageInput('passportBackPage'),
    ]);

    await h1BUpdatePage.save();
    expect(await h1BUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await h1BComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last H1B', async () => {
    const nbButtonsBeforeDelete = await h1BComponentsPage.countDeleteButtons();
    await h1BComponentsPage.clickOnLastDeleteButton();

    h1BDeleteDialog = new H1BDeleteDialog();
    expect(await h1BDeleteDialog.getDialogTitle()).to.eq('cloudfenApp.h1B.delete.question');
    await h1BDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(h1BComponentsPage.title), 5000);

    expect(await h1BComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
