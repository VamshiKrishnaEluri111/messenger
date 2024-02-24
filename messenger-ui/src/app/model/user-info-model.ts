export class UserInfoModel {
    constructor(
    public name: string,
    public username: string,
    public password: string,
    public authorities: string = "USER",
    public isLoggedIn: Boolean = false,
    ){}
}
