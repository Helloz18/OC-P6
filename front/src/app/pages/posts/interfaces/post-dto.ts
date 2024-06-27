export interface PostCreateDTO {
    title: string,
    content: string,
    topicId: number
}

export interface PostDTO {
    id: number,
    title: string,
    content: string,
    author: string,
    createdAt: string
}

export interface PostDetailDTO {
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