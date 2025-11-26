import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import postService from '../services/postService';
import { Post } from '../types';

export const usePosts = () => {
  const queryClient = useQueryClient();

  const { data: posts, isLoading: isLoadingPosts, error: postsError } = useQuery<Post[]>({
    queryKey: ['posts'],
    queryFn: postService.getAll,
  });

  const createPostMutation = useMutation({
    mutationFn: (post: Partial<Post>) => postService.save(post),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['posts'] });
    },
  });

  const updatePostMutation = useMutation({
    mutationFn: ({ id, post }: { id: number; post: Partial<Post> }) => postService.update(id, post),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['posts'] });
    },
  });

  const deletePostMutation = useMutation({
    mutationFn: (id: number) => postService.remove(id),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['posts'] });
    },
  });

  return {
    posts,
    isLoadingPosts,
    postsError,
    createPost: createPostMutation.mutate,
    isCreatingPost: createPostMutation.isPending,
    createPostError: createPostMutation.error,
    updatePost: updatePostMutation.mutate,
    isUpdatingPost: updatePostMutation.isPending,
    updatePostError: updatePostMutation.error,
    deletePost: deletePostMutation.mutate,
    isDeletingPost: deletePostMutation.isPending,
    deletePostError: deletePostMutation.error,
  };
};
