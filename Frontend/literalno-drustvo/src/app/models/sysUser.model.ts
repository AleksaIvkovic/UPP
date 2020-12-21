export class SysUser {
    constructor(
        public firstname: string,
        public lastname: string,
        public city: string,
        public country: string,
        public username: string,
        public email: string,
        public isBeta: boolean,
        public authority: string,
    ) {}
}