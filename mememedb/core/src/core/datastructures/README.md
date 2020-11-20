# The datastructures package

The datastructures package, representing part of the domain layer, is split into three main classes:

- **Post** - a post with a caption, owner and an image.
- **User** - an user with a unique username, name, email, a hashed password, and a list of their post ids.
- **Comment** - a comment with a text, author, and a timestamp. Belongs to a specific post.