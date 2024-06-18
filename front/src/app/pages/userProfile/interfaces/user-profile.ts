import { Topic } from "../../topics/interfaces/topic";

export interface UserProfile {

    id?: number,
    name: string,
    email: string,
    password?: string,
    topics?: Topic[]
}
