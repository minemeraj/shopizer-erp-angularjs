import { ShopizerAdminUiPage } from './app.po';

describe('shopizer-admin-ui App', () => {
  let page: ShopizerAdminUiPage;

  beforeEach(() => {
    page = new ShopizerAdminUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
