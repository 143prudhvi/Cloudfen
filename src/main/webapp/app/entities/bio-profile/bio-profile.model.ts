export interface IBioProfile {
  id?: number;
  userName?: string | null;
  userId?: string | null;
  memberId?: string | null;
  firstName?: string | null;
  lastName?: string | null;
  dob?: string | null;
  gender?: string | null;
  imageUrl?: string | null;
  title?: string | null;
  summary?: string | null;
}

export class BioProfile implements IBioProfile {
  constructor(
    public id?: number,
    public userName?: string | null,
    public userId?: string | null,
    public memberId?: string | null,
    public firstName?: string | null,
    public lastName?: string | null,
    public dob?: string | null,
    public gender?: string | null,
    public imageUrl?: string | null,
    public title?: string | null,
    public summary?: string | null
  ) {}
}

export function getBioProfileIdentifier(bioProfile: IBioProfile): number | undefined {
  return bioProfile.id;
}
