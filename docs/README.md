## Blitz User Guide

![Image](Ui.png)

Take a look at Blitz, a chatbot that is perfect for handling your everyday tasks! It's
- text-based
- easy to learn
- _SUPER FAST_ to use!

## Features

## Adding todo
Adds a todo task to the task list, with the uncompleted state.

Example: `todo running`

Expected outcome:
`[T][] running`

Expected output:
`[T][] running`

## Adding deadlines
Adds a Deadline task to the task list, with the uncompleted state.

Example: `deadline CS2103T assignment /by Friday 4pm`

Expected outcome:
`[D][] CS2103T assignment (by: Friday 4pm)`

Expected output:
`[D][] CS2103T assignment (by: Friday 4pm)`

## Adding events
Adds a Event task to the task list, with the uncompleted state.

Example: `event travelling overseas /from 2026-02-23 /to 2026-03-02`

Expected outcome:  
`[E][] travelling overseas (from: 2026-02-23 to: 2026-03-02)`

Expected output:  
`[E][] travelling overseas (from: 2026-02-23 to: 2026-03-02)`

## Listing
Displays all tasks in the task list.

Example: `list`

## Mark and Unmark tasks
Marks or unmark tasks as completed or uncompleted accordingly, with an X, based on the index in the task list.

Example: `mark 1`  

Example: `unmark 1`  

Expected outcome:  
`[T][X] running`  

`[T][] running`  

Expected output:  
`[T][X] running`  

`[T][] running`  

## Deleting task
Deletes a task based on the index in the task list.

Example: `delete 1`

Expected outcome:
`Now you have 0 tasks in the list.`

Expected output:
`Now you have 0 tasks in the list.`

## Finding task
Finds a task based on the prefix of the task name  

Example: `find r`  

Example: `find ru`  

Example: `find run`  

Expected outcome: 
`[T][] running`

Expected output:  
`[T][] running`

## Exit
Exits program after a short delay

Example: `bye`