export class MessageInfo {
  constructor(
    public id: number,
    public sender: String,
    public reciever: String,
    public message: String,
    public isOpened: Boolean,
    public createdBy: String,
    public fileName: String,
		public fileData: string,
		public sentTimeInString: string,
		public seenTimeInString: string,
    public senderMessageDeleted:string,
    public recieverMessageDeleted:string
  ) {}
}
