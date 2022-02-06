export interface ILoginProfile {
  id?: number;
  userName?: string | null;
  userId?: string | null;
  memberId?: string | null;
  phoneNumber?: string | null;
  emailId?: string | null;
  password?: string | null;
  status?: string | null;
  activationCode?: string | null;
}

export class LoginProfile implements ILoginProfile {
  constructor(
    public id?: number,
    public userName?: string | null,
    public userId?: string | null,
    public memberId?: string | null,
    public phoneNumber?: string | null,
    public emailId?: string | null,
    public password?: string | null,
    public status?: string | null,
    public activationCode?: string | null
  ) {}
}

export function getLoginProfileIdentifier(loginProfile: ILoginProfile): number | undefined {
  return loginProfile.id;
}
