import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { BachelorDegreeComponentsPage, BachelorDegreeDeleteDialog, BachelorDegreeUpdatePage } from './bachelor-degree.page-object';

const expect = chai.expect;

describe('BachelorDegree e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let bachelorDegreeComponentsPage: BachelorDegreeComponentsPage;
  let bachelorDegreeUpdatePage: BachelorDegreeUpdatePage;
  let bachelorDegreeDeleteDialog: BachelorDegreeDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load BachelorDegrees', async () => {
    await navBarPage.goToEntity('bachelor-degree');
    bachelorDegreeComponentsPage = new BachelorDegreeComponentsPage();
    await browser.wait(ec.visibilityOf(bachelorDegreeComponentsPage.title), 5000);
    expect(await bachelorDegreeComponentsPage.getTitle()).to.eq('cloudfenApp.bachelorDegree.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(bachelorDegreeComponentsPage.entities), ec.visibilityOf(bachelorDegreeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create BachelorDegree page', async () => {
    await bachelorDegreeComponentsPage.clickOnCreateButton();
    bachelorDegreeUpdatePage = new BachelorDegreeUpdatePage();
    expect(await bachelorDegreeUpdatePage.getPageTitle()).to.eq('cloudfenApp.bachelorDegree.home.createOrEditLabel');
    await bachelorDegreeUpdatePage.cancel();
  });

  it('should create and save BachelorDegrees', async () => {
    const nbButtonsBeforeCreate = await bachelorDegreeComponentsPage.countDeleteButtons();

    await bachelorDegreeComponentsPage.clickOnCreateButton();

    await promise.all([bachelorDegreeUpdatePage.setCourseInput('course')]);

    await bachelorDegreeUpdatePage.save();
    expect(await bachelorDegreeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await bachelorDegreeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last BachelorDegree', async () => {
    const nbButtonsBeforeDelete = await bachelorDegreeComponentsPage.countDeleteButtons();
    await bachelorDegreeComponentsPage.clickOnLastDeleteButton();

    bachelorDegreeDeleteDialog = new BachelorDegreeDeleteDialog();
    expect(await bachelorDegreeDeleteDialog.getDialogTitle()).to.eq('cloudfenApp.bachelorDegree.delete.question');
    await bachelorDegreeDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(bachelorDegreeComponentsPage.title), 5000);

    expect(await bachelorDegreeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
