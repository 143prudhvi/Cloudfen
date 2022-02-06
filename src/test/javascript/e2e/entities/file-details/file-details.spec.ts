import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FileDetailsComponentsPage, FileDetailsDeleteDialog, FileDetailsUpdatePage } from './file-details.page-object';

const expect = chai.expect;

describe('FileDetails e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let fileDetailsComponentsPage: FileDetailsComponentsPage;
  let fileDetailsUpdatePage: FileDetailsUpdatePage;
  let fileDetailsDeleteDialog: FileDetailsDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FileDetails', async () => {
    await navBarPage.goToEntity('file-details');
    fileDetailsComponentsPage = new FileDetailsComponentsPage();
    await browser.wait(ec.visibilityOf(fileDetailsComponentsPage.title), 5000);
    expect(await fileDetailsComponentsPage.getTitle()).to.eq('cloudfenApp.fileDetails.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(fileDetailsComponentsPage.entities), ec.visibilityOf(fileDetailsComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FileDetails page', async () => {
    await fileDetailsComponentsPage.clickOnCreateButton();
    fileDetailsUpdatePage = new FileDetailsUpdatePage();
    expect(await fileDetailsUpdatePage.getPageTitle()).to.eq('cloudfenApp.fileDetails.home.createOrEditLabel');
    await fileDetailsUpdatePage.cancel();
  });

  it('should create and save FileDetails', async () => {
    const nbButtonsBeforeCreate = await fileDetailsComponentsPage.countDeleteButtons();

    await fileDetailsComponentsPage.clickOnCreateButton();

    await promise.all([
      fileDetailsUpdatePage.setUserIdInput('userId'),
      fileDetailsUpdatePage.setUserNameInput('userName'),
      fileDetailsUpdatePage.setTypeInput('type'),
      fileDetailsUpdatePage.setFileNameInput('fileName'),
    ]);

    await fileDetailsUpdatePage.save();
    expect(await fileDetailsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await fileDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FileDetails', async () => {
    const nbButtonsBeforeDelete = await fileDetailsComponentsPage.countDeleteButtons();
    await fileDetailsComponentsPage.clickOnLastDeleteButton();

    fileDetailsDeleteDialog = new FileDetailsDeleteDialog();
    expect(await fileDetailsDeleteDialog.getDialogTitle()).to.eq('cloudfenApp.fileDetails.delete.question');
    await fileDetailsDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(fileDetailsComponentsPage.title), 5000);

    expect(await fileDetailsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
