"""
In our system, each employee has a role, and roles are organised in a hierarchy. Each role (except the top-level role) reports to a parent role.

For Example:

The “System administrator” role has no parent (it is the top-level role)

The “Location Manager” role reports to the “System Administrator.”

The “Supervisor” role reports to the “Location Manager”, and so on.

Given a list of roles and users, write a function/s that, for a given employee, returns all employees who report to them Directly or Indirectly through the role hierarchy.

Input:

roles: A list of role objects, where each role has:

Id: A unique number identifying the role

Name: The name of the role

Parent: The ID of the parent role (0 if this is a top-level role)

Users: A list of employee objects, where each employee has:

Id: A unique number identifying the employee

Name: The name of the employee

Role: The ID of the role the employee belongs to.

user_id - the ID of the employee we are searching.

Output:

A list of all employees who report to the given employee, either directly or indirectly

Constraints:

The roles hierarchy is a valid tree (No Cycles)

Each employee has one role

Example Input:

const roles = [
    { Id: 1, Name: "System Administrator", Parent: 0 },
    { Id: 2, Name: "Location Manager", Parent: 1 },
    { Id: 3, Name: "Supervisor", Parent: 2 },
    { Id: 4, Name: "Employee", Parent: 3 },
    { Id: 5, Name: "Trainer", Parent: 3 },
];
const users = [
    { Id: 1, Name: "Adam Admin", Role: 1 },
    { Id: 2, Name: "Emily Employee", Role: 4 },
    { Id: 3, Name: "Sam Supervisor", Role: 3 },
    { Id: 4, Name: "Mary Manager", Role: 2 },
    { Id: 5, Name: "Steve Trainer", Role: 5 },
];

Example Queries & Expected Output:

console.log(get_subordinates(3)); 
Output: Emily Employee, Steve Trainer
console.log(get_subordinates(1)); 
Output: Mary Manager, Sam Supervisor, Emily Employee, Steve Trainer
"""



from collections import defaultdict, deque

class RoleHierarchy:
    def __init__(self, roles, users):
        self.role_hierarchy = defaultdict(list)
        for role in roles:
            self.role_hierarchy[role["Parent"]].append(role["Id"])
        self.role_to_user = {user["Id"]: user["Role"] for user in users}
        self.user_to_name = {user["Id"]: user["Name"] for user in users}
    
    def get_subordinates(self, user_id):
        if user_id not in self.role_to_user:
            return []
        
        root_role = self.role_to_user[user_id]
        sub_roles = set()
        queue = deque([root_role])
        
        while queue:
            role = queue.popleft()
            sub_roles.add(role)
            queue.extend(self.role_hierarchy[role])
        
        subordinates = [self.user_to_name[uid] for uid, role in self.role_to_user.items() if role in sub_roles and uid != user_id]
        return subordinates

roles = [
    {"Id": 1, "Name": "System Administrator", "Parent": 0},
    {"Id": 2, "Name": "Location Manager", "Parent": 1},
    {"Id": 3, "Name": "Supervisor", "Parent": 2},
    {"Id": 4, "Name": "Employee", "Parent": 3},
    {"Id": 5, "Name": "Trainer", "Parent": 3},
]

users = [
    {"Id": 1, "Name": "Adam Admin", "Role": 1},
    {"Id": 2, "Name": "Emily Employee", "Role": 4},
    {"Id": 3, "Name": "Sam Supervisor", "Role": 3},
    {"Id": 4, "Name": "Mary Manager", "Role": 2},
    {"Id": 5, "Name": "Steve Trainer", "Role": 5},
]

# test cases
# $ python3 18-02-25-role_hierarchy.py
hierarchy = RoleHierarchy(roles, users)

print(hierarchy.get_subordinates(3))  # Output: ['Emily Employee', 'Steve Trainer']
print(hierarchy.get_subordinates(1))  # Output: ['Mary Manager', 'Sam Supervisor', 'Emily Employee', 'Steve Trainer']