import { usePosts } from '../hooks/usePosts';
import type { Post } from '../types';

export default function Posts() {
  const { posts, isLoadingPosts, postsError, deletePost } = usePosts();

  if (isLoadingPosts) {
    return <div>Loading...</div>;
  }

  if (postsError) {
    return <div>Error: {postsError.message}</div>;
  }

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Posts</h1>
      <button
        // onClick={() => createPost({ title: 'New Post', content: 'New Content' })}
        className="bg-blue-500 text-white px-4 py-2 rounded mb-4"
      >
        Create Post
      </button>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {posts?.map((post: Post) => (
          <div key={post.postId} className="bg-gray-800 p-4 rounded">
            <h2 className="text-xl font-bold">{post.title}</h2>
            <p>{post.content}</p>
            <div className="mt-4">
              <button
                // onClick={() => updatePost({ id: post.postId, post: { title: 'Updated Title' } })}
                className="bg-green-500 text-white px-2 py-1 rounded mr-2"
              >
                Edit
              </button>
              <button
                onClick={() => deletePost(post.postId)}
                className="bg-red-500 text-white px-2 py-1 rounded"
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
