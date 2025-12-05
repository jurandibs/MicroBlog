import { useState } from 'react';
import { usePosts } from '../hooks/usePosts';
import type { Post } from '../types';

export default function Posts() {
  const { posts, isLoadingPosts, postsError, createPost, deletePost } = usePosts();
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');

  if (isLoadingPosts) {
    return <div>Loading...</div>;
  }

  if (postsError) {
    return <div>Error: {postsError.message}</div>;
  }

  const handleCreatePost = (e: React.FormEvent) => {
    e.preventDefault();
    createPost({ title, content });
    setTitle('');
    setContent('');
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4 text-white">Posts</h1>

      <form onSubmit={handleCreatePost} className="bg-gray-800 p-4 rounded mb-8">
        <h2 className="text-xl font-bold mb-4 text-white">Create New Post</h2>
        <div className="mb-4">
          <label className="block text-gray-300 mb-2" htmlFor="title">Title</label>
          <input
            id="title"
            type="text"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="w-full p-2 rounded bg-gray-700 text-white border border-gray-600"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-300 mb-2" htmlFor="content">Content</label>
          <textarea
            id="content"
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="w-full p-2 rounded bg-gray-700 text-white border border-gray-600"
            required
          />
        </div>
        <button
          type="submit"
          className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600"
        >
          Publish Post
        </button>
      </form>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {posts?.map((post: Post) => (
          <div key={post.postId} className="bg-gray-800 p-4 rounded text-white">
            <h2 className="text-xl font-bold mb-2">{post.title}</h2>
            <p className="text-gray-300 mb-4">{post.content}</p>
            <div className="flex gap-2">
              <button
                onClick={() => deletePost(post.postId)}
                className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
