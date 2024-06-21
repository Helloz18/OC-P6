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

export interface PostDTO {
    title: string,
    content: string,
    author: string,
    topicName: string,
    createdAt: string,
    comments: CommentDTO[]
}

export interface CommentDTO {
    author: string,
    content: string
}