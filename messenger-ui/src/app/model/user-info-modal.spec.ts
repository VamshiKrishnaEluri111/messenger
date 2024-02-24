import { UserInfoModel } from './user-info-model';

describe('UserInfoModal', () => {
  it('should create an instance', () => {
    expect(new UserInfoModel("","","","",false)).toBeTruthy();
  });
});
