export interface IFileDetails {
  id?: number;
  userId?: string | null;
  userName?: string | null;
  type?: string | null;
  fileName?: string | null;
}

export class FileDetails implements IFileDetails {
  constructor(
    public id?: number,
    public userId?: string | null,
    public userName?: string | null,
    public type?: string | null,
    public fileName?: string | null
  ) {}
}

export function getFileDetailsIdentifier(fileDetails: IFileDetails): number | undefined {
  return fileDetails.id;
}
