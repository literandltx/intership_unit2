## Quick start
1. Must be installed java 21, maven, docker
2. Start PostgreSQL docker container
```
docker run -d --name postgres-db -p 5432:5432 -e POSTGRES_PASSWORD=password postgres
```
3. Build the Application
```
mvn clean package
```
4. Run the Application
```
mvn spring-boot:run -Dspring.profiles.active=dev
```

## Domain Description

The cart items management system domain encompasses the following entities and their relationships:

1. **BaseEntity:**
    - Provides common attributes shared by all entities.
    - Attributes:
        - `id`: Unique identifier for each entity.
        - Creation and modification timestamps.

2. **CartItem:**
    - Represents an item that can be added to the shopping cart.
    - Attributes:
        - `title`: The title or name of the item.
        - `description`: A description providing additional details about the item.
        - `rank`: An optional ranking value for prioritizing items.
    - Relationships:
        - Belongs to a specific `Group`.
        - Can have multiple `Label`s for categorization.

3. **Group:**
    - Represents a category or group under which items are organized.
    - Attributes:
        - `title`: The unique title of the group.
    - Relationships:
        - Contains multiple `CartItem`s.

4. **Label:**
    - Represents a label that can be assigned to items for categorization.
    - Attributes:
        - `name`: The name or title of the label.
    - Relationships:
        - Associated with a specific `CartItem`.

## Endpoints Description

### Cart Item Controller (CartItemControllerV1)

- `POST /api/v1/items`: Creates a new cart item.
```json
{
    "title": "save_title",
    "description": "description1",
    "rank": 8.8,
    "groupId": 2
}
```
- `PUT /api/v1/items`: Updates a cart item by ID.
- `DELETE /api/v1/items`: Deletes a cart item by ID.
- `GET /api/v1/items`: Retrieves a cart item by ID.
- `GET /api/v1/items/_list?size=10&page=0`: Retrieves a paginated list of all existing cart items. Size and page are optional
- `POST /api/v1/items/upload`: Uploads a file containing cart item data.
- `POST /api/v1/items/_report`: Generates and downloads a report based on search criteria.
All/some of the filtering fields can be missing
```json
{
  "titles": ["title1", "title2"],
  "description": ["description1", "description2"],
  "rank": {
    "min": 1.0,
    "max": 10.0
  },
  "groupIds": [1, 2, 3]
}
```
### Label Controller (LabelControllerV1)
- `GET /api/v1/labels`: Retrieves a list of all existing Label records.
- `POST /api/v1/labels`: Creates a new Label record.
```json
{
    "cartItemId": 1,
    "name": "label1"
}
```
- `PUT /api/v1/labels?id=label1_id`: Updates a Label record by ID, considering the uniqueness of names.
```json
{
    "name": "updated_label1"
}
```
- `DELETE /api/v1/labels?id=_id`: Deletes a Label record by ID.

### Group Controller (GroupControllerV1)

- `POST /api/v1/groups`: Creates a new group.
```json
{
    "title": "new_title"
}
```
- `GET /api/v1/groups`: Retrieves a list of all existing groups.
- `PUT /api/v1/groups`: Updates a group by ID.
- `POST /api/v1/groups`: Creates a new group.
```json
{
    "title": "updated_title"
}
```
- `DELETE /api/v1/groups`: Deletes a group by ID.

## Request Example

Example of input file content for: `/api/v1/items/upload` 
```json
[
  {
    "title": "title1",
    "description": "description1",
    "rank": 9.9,
    "groupId": 1,
    "labels": "label1, label2"
  },
  {
    "title": "title2",
    "description": "description",
    "rank": 8.8,
    "groupId": 2,
    "labels": "label1"
  },
  {
    "title": "title3",
    "description": "description",
    "rank": 7.7,
    "groupId": 3,
    "labels": "label1, label2, label3"
  },
  {
    "title": "title4",
    "description": "description",
    "rank": 6.6,
    "groupId": 1,
    "labels": "label1"
  }
]   
```

## Liquibase inserted entities
```json
[
  {
    "id": 1,
    "title": "group_title1"
  },
  {
    "id": 2,
    "title": "group_title2"
  },
  {
    "id": 3,
    "title": "group_title3"
  }
]
```