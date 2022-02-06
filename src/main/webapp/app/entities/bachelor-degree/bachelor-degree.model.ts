export interface IBachelorDegree {
  id?: number;
  course?: string | null;
}

export class BachelorDegree implements IBachelorDegree {
  constructor(public id?: number, public course?: string | null) {}
}

export function getBachelorDegreeIdentifier(bachelorDegree: IBachelorDegree): number | undefined {
  return bachelorDegree.id;
}
