import { Topic } from "../../topic/interfaces/topic";

export interface UserProfile {

    id?: number,
    name: string,
    email: string,
    password?: string,
    topics?: Topic[]
}
