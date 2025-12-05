export interface LoginRequest {
  username?: string;
  email?: string;
  password?: string;
}

export interface User {
  userId: number;
  username: string;
  email: string;
}

export interface Tag {
  tagId: number;
  name: string;
}

export interface Post {
  postId: number;
  title: string;
  content: string;
  date: string;
  userId: User;
  tagId: Tag[];
}

export interface RegisterRequest {
  username?: string;
  email?: string;
  password?: string;
}
