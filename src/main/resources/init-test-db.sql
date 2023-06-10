
INSERT INTO public.users(id, slug, name, create_date, password, email, status) SELECT 10000, 'slug', 'Danil', CURRENT_DATE, '123456', 'test@test.com', 1 WHERE NOT EXISTS ( SELECT 10000 FROM public.users WHERE id = 10000);-- пользователь id 1 подписан на пользователя id 2, нужны для тестирования логики получения постов друга

INSERT INTO public.users(id, slug, name, create_date, password, email, status) SELECT 10001, 'slug1', 'Danil', CURRENT_DATE, '123456', 'test1@test.com', 1 WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE id = 10001);

INSERT INTO public.users(id, slug, name, create_date, password, email, status) SELECT 10002, 'slug2', 'Danil', CURRENT_DATE, '123456', 'test2@test.com', 1 WHERE NOT EXISTS ( SELECT 1 FROM public.users WHERE id = 10002);-- здесь пользователи заранее на друг друга не подписаны, нужны для тестирования логики подписки и добавления в друзья

INSERT INTO public.users(id, slug, name, create_date, password, email, status) SELECT 10003, 'slug3', 'Danil', CURRENT_DATE, '123456', 'test3@test.com', 1 WHERE NOT EXISTS (SELECT 1 FROM public.users WHERE id = 10003);

INSERT INTO public.user_contact(id, user_id, type, approved, code_time, code, contact) SELECT 1000000, 10000, 1, true, CURRENT_DATE, 123456, 'test@test.com' WHERE NOT EXISTS (SELECT 1 FROM public.user_contact WHERE contact = 'test@test.com');

INSERT INTO public.user_contact(id, user_id, type, approved, code_time, code, contact) SELECT 1000001, 10001, 1, true, CURRENT_DATE, 123456, 'test1@test.com' WHERE NOT EXISTS (SELECT 1 FROM public.user_contact WHERE contact = 'test1@test.com');

INSERT INTO public.user_contact(id, user_id, type, approved, code_time, code, contact) SELECT 1000002, 10002, 1, true, CURRENT_DATE, 123456, 'test2@test.com' WHERE NOT EXISTS (SELECT 1 FROM public.user_contact WHERE contact = 'test2@test.com');

INSERT INTO public.user_contact(id, user_id, type, approved, code_time, code, contact) SELECT 1000003, 10003, 1, true, CURRENT_DATE, 123456, 'test3@test.com' WHERE NOT EXISTS (SELECT 1 FROM public.user_contact WHERE contact = 'test3@test.com');

INSERT INTO public.followers(follower_id, user_id) SELECT 10000, 10001 WHERE NOT EXISTS (SELECT 1 FROM public.followers WHERE follower_id = 10000 AND user_id = 10001);

INSERT INTO public.followers(follower_id, user_id) SELECT 10001, 10000 WHERE NOT EXISTS (SELECT 1 FROM public.followers WHERE follower_id = 10001 AND user_id = 10000);

INSERT INTO public.friends(friend_id, user_id) SELECT 10001, 10000 WHERE NOT EXISTS (SELECT 1 FROM public.friends WHERE friend_id = 10001 AND user_id = 10000);

INSERT INTO public.user2roles(role_id, user_id) SELECT 1, 10001 WHERE NOT EXISTS (SELECT 1 FROM public.user2roles WHERE role_id = 1 AND user_id = 10000);

INSERT INTO public.user2roles(role_id, user_id) SELECT 1, 10001 WHERE NOT EXISTS (SELECT 1 FROM public.user2roles WHERE role_id = 1 AND user_id = 10001);

INSERT INTO public.user2roles(role_id, user_id) SELECT 1, 10002 WHERE NOT EXISTS (SELECT 1 FROM public.user2roles WHERE role_id = 1 AND user_id = 10002);

INSERT INTO public.user2roles(role_id, user_id) SELECT 1, 10003 WHERE NOT EXISTS (SELECT 1 FROM public.user2roles WHERE role_id = 1 AND user_id = 10003);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000000, 'post-slug', 'title', 'description', '/path', 10000, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000000);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000002, 'post-slug1', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000002);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000003, 'post-slug2', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000003);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000004, 'post-slug3', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000004);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000005, 'post-slug4', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000005);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000006, 'post-slug5', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000006);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000007, 'post-slug6', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000007);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000008, 'post-slug7', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000008);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000009, 'post-slug8', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000009);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000010, 'post-slug9', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000010);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000011, 'post-slug10', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000011);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000012, 'post-slug11', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000012);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000013, 'post-slug12', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000013);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000014, 'post-slug13', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000014);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000015, 'post-slug14', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000015);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000016, 'post-slug15', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000016);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000017, 'post-slug16', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000017);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000018, 'post-slug17', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000018);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000019, 'post-slug18', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000019);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000020, 'post-slug19', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000020);

INSERT INTO public.posts(id, slug, title, description, image, user_id, create_date) SELECT 1000021, 'post-slug20', 'title', 'description', '/path', 10001, CURRENT_DATE WHERE NOT EXISTS (SELECT 1 FROM public.posts WHERE id = 1000021);