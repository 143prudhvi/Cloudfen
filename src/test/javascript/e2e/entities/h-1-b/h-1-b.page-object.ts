import { element, by, ElementFinder } from 'protractor';

export class H1BComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-h-1-b div table .btn-danger'));
  title = element.all(by.css('jhi-h-1-b div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class H1BUpdatePage {
  pageTitle = element(by.id('jhi-h-1-b-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  userIdInput = element(by.id('field_userId'));
  firstNameInput = element(by.id('field_firstName'));
  middleNameInput = element(by.id('field_middleName'));
  lastNameInput = element(by.id('field_lastName'));
  dateOfBirthInput = element(by.id('field_dateOfBirth'));
  countryOfBirthInput = element(by.id('field_countryOfBirth'));
  countryOfCitizenShipInput = element(by.id('field_countryOfCitizenShip'));
  passportNumberInput = element(by.id('field_passportNumber'));
  genderSelect = element(by.id('field_gender'));
  categorySelect = element(by.id('field_category'));
  emailInput = element(by.id('field_email'));
  currentAddressInput = element(by.id('field_currentAddress'));
  phoneNumberInput = element(by.id('field_phoneNumber'));
  bachelorDegreeInput = element(by.id('field_bachelorDegree'));
  yearInput = element(by.id('field_year'));
  currentVisaStatusInput = element(by.id('field_currentVisaStatus'));
  referedByInput = element(by.id('field_referedBy'));
  passportFrontPageInput = element(by.id('field_passportFrontPage'));
  passportBackPageInput = element(by.id('field_passportBackPage'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setUserIdInput(userId: string): Promise<void> {
    await this.userIdInput.sendKeys(userId);
  }

  async getUserIdInput(): Promise<string> {
    return await this.userIdInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName: string): Promise<void> {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput(): Promise<string> {
    return await this.middleNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setDateOfBirthInput(dateOfBirth: string): Promise<void> {
    await this.dateOfBirthInput.sendKeys(dateOfBirth);
  }

  async getDateOfBirthInput(): Promise<string> {
    return await this.dateOfBirthInput.getAttribute('value');
  }

  async setCountryOfBirthInput(countryOfBirth: string): Promise<void> {
    await this.countryOfBirthInput.sendKeys(countryOfBirth);
  }

  async getCountryOfBirthInput(): Promise<string> {
    return await this.countryOfBirthInput.getAttribute('value');
  }

  async setCountryOfCitizenShipInput(countryOfCitizenShip: string): Promise<void> {
    await this.countryOfCitizenShipInput.sendKeys(countryOfCitizenShip);
  }

  async getCountryOfCitizenShipInput(): Promise<string> {
    return await this.countryOfCitizenShipInput.getAttribute('value');
  }

  async setPassportNumberInput(passportNumber: string): Promise<void> {
    await this.passportNumberInput.sendKeys(passportNumber);
  }

  async getPassportNumberInput(): Promise<string> {
    return await this.passportNumberInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setCategorySelect(category: string): Promise<void> {
    await this.categorySelect.sendKeys(category);
  }

  async getCategorySelect(): Promise<string> {
    return await this.categorySelect.element(by.css('option:checked')).getText();
  }

  async categorySelectLastOption(): Promise<void> {
    await this.categorySelect.all(by.tagName('option')).last().click();
  }

  async setEmailInput(email: string): Promise<void> {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput(): Promise<string> {
    return await this.emailInput.getAttribute('value');
  }

  async setCurrentAddressInput(currentAddress: string): Promise<void> {
    await this.currentAddressInput.sendKeys(currentAddress);
  }

  async getCurrentAddressInput(): Promise<string> {
    return await this.currentAddressInput.getAttribute('value');
  }

  async setPhoneNumberInput(phoneNumber: string): Promise<void> {
    await this.phoneNumberInput.sendKeys(phoneNumber);
  }

  async getPhoneNumberInput(): Promise<string> {
    return await this.phoneNumberInput.getAttribute('value');
  }

  async setBachelorDegreeInput(bachelorDegree: string): Promise<void> {
    await this.bachelorDegreeInput.sendKeys(bachelorDegree);
  }

  async getBachelorDegreeInput(): Promise<string> {
    return await this.bachelorDegreeInput.getAttribute('value');
  }

  async setYearInput(year: string): Promise<void> {
    await this.yearInput.sendKeys(year);
  }

  async getYearInput(): Promise<string> {
    return await this.yearInput.getAttribute('value');
  }

  async setCurrentVisaStatusInput(currentVisaStatus: string): Promise<void> {
    await this.currentVisaStatusInput.sendKeys(currentVisaStatus);
  }

  async getCurrentVisaStatusInput(): Promise<string> {
    return await this.currentVisaStatusInput.getAttribute('value');
  }

  async setReferedByInput(referedBy: string): Promise<void> {
    await this.referedByInput.sendKeys(referedBy);
  }

  async getReferedByInput(): Promise<string> {
    return await this.referedByInput.getAttribute('value');
  }

  async setPassportFrontPageInput(passportFrontPage: string): Promise<void> {
    await this.passportFrontPageInput.sendKeys(passportFrontPage);
  }

  async getPassportFrontPageInput(): Promise<string> {
    return await this.passportFrontPageInput.getAttribute('value');
  }

  async setPassportBackPageInput(passportBackPage: string): Promise<void> {
    await this.passportBackPageInput.sendKeys(passportBackPage);
  }

  async getPassportBackPageInput(): Promise<string> {
    return await this.passportBackPageInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class H1BDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-h1B-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-h1B'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
