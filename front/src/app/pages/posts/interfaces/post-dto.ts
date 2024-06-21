export interface PostCreateDTO {
    title: string,
    content: string,
    topicId: number
}

export interface PostForListDTO {
    id: number,
    title: string,
    content: string,
    author: string,
    createdAt: string
}