############################################   Пользователи

#### достать пользователя по id (только для авторизированных пользователей) 
`curl -s GET http://localhost:8080/users/auth/3` (status 401)
`curl -s GET http://localhost:8080/users/auth/3 --user admin@gmail.com:admin` (status 200)

#### достать вообще всех пользователей (только для авторизированных пользователей) 
`curl -s GET http://localhost:8080/users/auth/all` (status 401)
`curl -s GET http://localhost:8080/users/auth/all --user admin@gmail.com:admin` (status 200)

#### достать только обычных пользователей (только для авторизированных пользователей) 
`curl -s GET http://localhost:8080/users/auth/get_us?option=user` (status 401)
`curl -s GET http://localhost:8080/users/auth/get_us?option=user --user admin@gmail.com:admin` (status 200)

#### достать только администраторов ресторанов (только для авторизированных пользователей) 
`curl -s GET http://localhost:8080/users/auth/get_us?option=admin` (status 401)
`curl -s GET http://localhost:8080/users/auth/get_us?option=admin --user admin@gmail.com:admin` (status 200)

#### удалить текущего пользователя (только для хозяина учетной записи) 
`curl -X DELETE http://localhost:8080/users/auth/delete_user` (status 401)
`curl -X DELETE http://localhost:8080/users/auth/delete_user --user user@yandex.ru:password` (status 200)

#### получить заготовку пользователя (доступно при регистрации, только для анонимных пользователей) 
`curl -s GET http://localhost:8080/users/anon/new_user?type=1` (status 200)

#### добавить нового пользователя (доступно для всех) 
`curl -X POST -v -d "{"""id""":null,"""name""":"""Create name""","""email""":"""create_email@yandex.ru""","""password""":"""create pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/users/all/create`  (status 200)

#### обновить cуществующего пользователя +
`curl -X POST -v -d "{"""id""":3,"""name""":"""Update name""","""email""":"""update_email@yandex.ru""","""password""":"""update_pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/users/all/create --user user@yandex.ru:password` (Обновляет не свою учетную запись. status 403)
`curl -X POST -v -d "{"""id""":3,"""name""":"""Update name""","""email""":"""update_email@yandex.ru""","""password""":"""update_pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/users/all/create --user anna2000@mail.com:anneta` (Обновляет свою учетную запись. status 200)
`curl -X POST -v -d "{"""id""":3,"""name""":"""Update name""","""email""":"""update_email@yandex.ru""","""password""":"""update_pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/users/all/create` (Аноним обновляет не свою учетную запись. status 401)

############################################   Меню

#### достать вообще все меню (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/menus/all/ms` (status 200)

#### достать все меню на конкретный день (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/menus/all/ms?date=2020-07-14` (status 200)

#### достать все меню для конкретного ресторана (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/menus/all/ms?id=2` (status 200)

#### достать все меню для конкретного ресторана на конкретный день (Доступно всем пользователям) 
`curl -d date=2020-07-15 -d id=2 -G http://localhost:8080/menus/all/ms` (status 200)

#### достать меню с наибольшим количеством голосов в требуемый день (Доступно только авторизованным пользователям) 
`curl -s GET http://localhost:8080/menus/auth/get_win/1?date=2020-07-14` (status 401)
`curl -s GET http://localhost:8080/menus/auth/get_win/1?date=2021-07-14 --user user@yandex.ru:password` (status 200)

#### достать меню с наименьшим количеством голосов в требуемый день (Доступно только авторизованным пользователям) 
`curl -s GET http://localhost:8080/menus/auth/get_win/0?date=2020-07-14` (status 401)
`curl -s GET http://localhost:8080/menus/auth/get_win/0?date=2020-07-14 --user user@yandex.ru:password` (status 200)

#### получить заготовку меню (Доступно только администраторам ресторанов) 
`curl -s GET http://localhost:8080/menus/admin/new_menu/0` (Аноним запрашивает заготовку. status 401)
`curl -s GET http://localhost:8080/menus/admin/new_menu/1 --user user@yandex.ru:password` (Обычный пользователь запрашивает заготовку. status 403)
`curl -s GET http://localhost:8080/menus/admin/new_menu/0 --user admin@gmail.com:admin` (Администратор ресторана запрашивает заготовку. status 200)

#### сохранить меню (Доступно только администраторам ресторанов) 
`curl -X POST -v -d "{"""idMenu""":null,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create` (Аноним сохраняет меню. status 401)
`curl -X POST -v -d "{"""idMenu""":null,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user user@yandex.ru:password` (Обычный пользователь сохраняет меню. status 403)
`curl -X POST -v -d "{"""idMenu""":null,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user admin@gmail.com:admin` (Администратор ресторана сохраняет меню не в свой ресторан. status 403)
`curl -X POST -v -d "{"""idMenu""":null,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user anna2000@mail.com:anneta` (Администратор ресторана сохраняет меню в свой ресторан. status 200)

#### обновить меню 
`curl -X POST -v -d "{"""idMenu""":4,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create` (Аноним обновляет чужое меню. status 401)
`curl -X POST -v -d "{"""idMenu""":4,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user user@yandex.ru:password` (Обычный пользователь обновляет чужое меню. status 403)
`curl -X POST -v -d "{"""idMenu""":4,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user admin@gmail.com:admin` (Администратор ресторана обновляет чужое меню. status 403)
`curl -X POST -v -d "{"""idMenu""":4,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/menus/admin/create --user anna2000@mail.com:anneta` (Администратор ресторана обновляет свое меню. status 200)

#### удалить меню 
`curl -X DELETE http://localhost:8080/menus/admin/4` (Аноним удаляет чужое меню. status 401)
`curl -X DELETE http://localhost:8080/menus/admin/4 --user user@yandex.ru:password` (Обычный пользователь удаляет чужое меню. status 403)
`curl -X DELETE http://localhost:8080/menus/admin/4 --user admin@gmail.com:admin` (Администратор ресторана удаляет чужое меню. status 403)
`curl -X DELETE http://localhost:8080/menus/admin/4 --user anna2000@mail.com:anneta` (Администратор ресторана удаляет свое меню. status 200)

############################################   Еда

#### достать вообще всю еду (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/meals/all` (status 200)

#### достать еду по id (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/meals/all/3` (status 200)

#### удалить еду по id (Доступно только администраторам ресторанов) 
`curl -X DELETE http://localhost:8080/meals/admin/3` (Аноним удаляет чужую еду. status 401)
`curl -X DELETE http://localhost:8080/meals/admin/3 --user user@yandex.ru:password` (Обычный пользователь удаляет чужую еду. status 403)
`curl -X DELETE http://localhost:8080/meals/admin/3 --user anna2000@mail.com:anneta` (Администратор ресторана удаляет чужую еду. status 403)
`curl -X DELETE http://localhost:8080/meals/admin/3 --user admin@gmail.com:admin` (Администратор ресторана удаляет свою еду. status 200)

#### достать всю еду на конкретный день (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/meals/all?date=2020-07-14` (status 200)

#### добавить новую еду (Доступно только администраторам ресторанов) 
`curl -X POST -v -d "{"""mealId""":null,"""menuId""":2,"""name""":"""new_name""","""description""":"""new_description""","""calories""":111,"""price""":99.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create` (Аноним добавляет новую еду. status 401)
`curl -X POST -v -d "{"""mealId""":null,"""menuId""":2,"""name""":"""new_name""","""description""":"""new_description""","""calories""":111,"""price""":99.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user user@yandex.ru:password` (Обычный пользователь добавляет новую еду. status 403)
`curl -X POST -v -d "{"""mealId""":null,"""menuId""":2,"""name""":"""new_name""","""description""":"""new_description""","""calories""":111,"""price""":99.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user anna2000@mail.com:anneta` (Администратор ресторана добавляет новую еду в меню другого ресторана. status 403)
`curl -X POST -v -d "{"""mealId""":null,"""menuId""":2,"""name""":"""new_name""","""description""":"""new_description""","""calories""":111,"""price""":99.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user admin@gmail.com:admin` (Администратор ресторана добавляет новую еду в меню своего ресторана. status 200)

#### обновить существующую еду (Доступно только администраторам ресторанов) 
`curl -X POST -v -d "{"""mealId""":40,"""menuId""":2,"""name""":"""update_name""","""description""":"""update_description""","""calories""":999,"""price""":11.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create` (Аноним обновляет чужую еду. status 401)
`curl -X POST -v -d "{"""mealId""":40,"""menuId""":2,"""name""":"""update_name""","""description""":"""update_description""","""calories""":999,"""price""":11.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user user@yandex.ru:password` (Обычный пользователь обновляет чужую  еду. status 403)
`curl -X POST -v -d "{"""mealId""":40,"""menuId""":2,"""name""":"""update_name""","""description""":"""update_description""","""calories""":999,"""price""":11.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user anna2000@mail.com:anneta` (Администратор ресторана обновляет чужую  еду. status 403)
`curl -X POST -v -d "{"""mealId""":40,"""menuId""":2,"""name""":"""update_name""","""description""":"""update_description""","""calories""":999,"""price""":11.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/meals/admin/create --user admin@gmail.com:admin` (Администратор ресторана обновляет свою еду. status 200)

#### получить заготовку еды (Доступно только администраторам ресторанов) 
`curl -s GET http://localhost:8080/meals/admin/new_meal/0 --user admin@gmail.com:admin` (status 200)

############################################   Рестораны

#### достать все рестораны (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/rest/all` (status 200)
`curl -s GET http://localhost:8080/rest/all --user admin@gmail.com:admin` (status 200)

#### достать рестораны по id владельца ресторана (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/rest/all?id_owner=1` (status 200)
`curl -s GET http://localhost:8080/rest/all?id_owner=1 --user admin@gmail.com:admin` (status 200)

#### достать ресторан по id (Доступно всем пользователям) 
`curl -s GET http://localhost:8080/rest/all/2` (status 200)
`curl -s GET http://localhost:8080/rest/all/2 --user admin@gmail.com:admin` (status 200)

#### удалить ресторан по id (Доступно только администраторам ресторанов) 
`curl -X DELETE http://localhost:8080/rest/admin/0` (Аноним удаляет чужой ресторан. status 401)
`curl -X DELETE http://localhost:8080/rest/admin/0 --user user@yandex.ru:password` (Обычный пользователь удаляет чужой ресторан. status 403)
`curl -X DELETE http://localhost:8080/rest/admin/0 --user anna2000@mail.com:anneta` (Администратор удаляет чужой ресторан. status 403)
`curl -X DELETE http://localhost:8080/rest/admin/0 --user admin@gmail.com:admin` (Администратор удаляет свой ресторан. status 200)

#### получить заготовку ресторана (Доступно только администраторам ресторанов) 
`curl -s GET http://localhost:8080/rest/admin/new_rest` (Аноним получает заготовку ресторана. status 401)
`curl -s GET http://localhost:8080/rest/admin/new_rest --user user@yandex.ru:password` (Обычный пользователь получает заготовку ресторана. status 403)
`curl -s GET http://localhost:8080/rest/admin/new_rest --user anna2000@mail.com:anneta` (Администратор получает заготовку ресторана. status 200)

#### сохранить ресторан (Доступно только администраторам ресторанов) 
`curl -X POST -v -d "{"""idRest""":null,"""idOwnerRest""":1,"""nameRest""":"""new_name_rest""","""address""":"""new_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create` (Аноним сохраняет ресторан. status 401)
`curl -X POST -v -d "{"""idRest""":null,"""idOwnerRest""":1,"""nameRest""":"""new_name_rest""","""address""":"""new_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create --user user@yandex.ru:password` (Обычный пользователь сохраняет ресторан. status 403)
`curl -X POST -v -d "{"""idRest""":null,"""idOwnerRest""":1,"""nameRest""":"""new_name_rest""","""address""":"""new_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create --user admin@gmail.com:admin` (Администратор сохраняет ресторан. status 200)

#### обновить ресторан (Доступно только администраторам ресторанов) 
`curl -X POST -v -d "{"""idRest""":0,"""idOwnerRest""":1,"""nameRest""":"""update_name_rest""","""address""":"""update_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create` (Аноним обновляет чужой ресторан. status 401)
`curl -X POST -v -d "{"""idRest""":0,"""idOwnerRest""":1,"""nameRest""":"""update_name_rest""","""address""":"""update_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create --user user@yandex.ru:password` (Обычный пользователь обновляет чужой ресторан. status 403)
`curl -X POST -v -d "{"""idRest""":0,"""idOwnerRest""":1,"""nameRest""":"""update_name_rest""","""address""":"""update_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create --user anna2000@mail.com:anneta` (Администратор обновляет чужой ресторан. status 403)
`curl -X POST -v -d "{"""idRest""":0,"""idOwnerRest""":1,"""nameRest""":"""update_name_rest""","""address""":"""update_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/admin/create --user admin@gmail.com:admin` (Администратор обновляет свой ресторан. status 200)

############################################   Голоса

#### достать вообще все отданные голоса (Доступно только обычным пользователям) 
`curl -s GET http://localhost:8080/vote/user/get_vote` (Аноним пытается просмотреть все положительные голоса. status 401)
`curl -s GET http://localhost:8080/vote/user/get_vote --user anna2000@mail.com:anneta` (Администратор ресторана пытается просмотреть все положительные голоса. status 403)
`curl -s GET http://localhost:8080/vote/user/get_vote --user user@yandex.ru:password` (Обычный пользователь пытается просмотреть все свои положительные голоса. status 200)

#### достать все положительные голоса отданные за конкретное меню (Доступно только авторизованным пользователям) 
`curl -d date=2021-07-14 -d menu_id=0 -G http://localhost:8080/vote/auth/menu` (Аноним пытается просмотреть все голоса за меню. status 401)
`curl -d date=2021-07-14 -d menu_id=0 -G http://localhost:8080/vote/auth/menu --user user@yandex.ru:password` (Обычный пытается просмотреть все голоса за меню. status 200)
`curl -d date=2021-07-14 -d menu_id=0 -G http://localhost:8080/vote/auth/menu --user anna2000@mail.com:anneta` (Администратор пытается просмотреть все голоса за меню. status 200)

#### достать голос для конкретного пользователя за конкретный день (Доступно только авторизованным пользователям) 
`curl -d date=2020-07-15 -d user_id=2 -G http://localhost:8080/vote/auth/user` (Аноним пытается просмотреть чужой голос за конкретный день. status 401)
`curl -d date=2020-07-15 -d user_id=2 -G http://localhost:8080/vote/auth/user --user user@yandex.ru:password` (Обычный пользователь пытается просмотреть чужой голос за конкретный день. status 200)
`curl -d date=2020-07-15 -d user_id=2 -G http://localhost:8080/vote/auth/user --user anna2000@mail.com:anneta` (Администратор пытается просмотреть чужой голос за конкретный день. status 200)

#### отдать голос за меню (Доступно только обычным пользователям)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":2,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true` (Аноним пытается проголосовать за меню (До истечения времени голосования). status 401)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":2,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true --user anna2000@mail.com:anneta` (Администратор пытается проголосовать за меню (До истечения времени голосования). status 403)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":2,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true --user stack@yandex.com:Itest` (Обычный пользователь пытается проголосовать за меню (До истечения времени голосования). status 200)

`curl -X POST -v -d "{"""idMenu""":1,"""idRest""":0,"""counterVoice""":0,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true` (Аноним пытается проголосовать за меню (После истечения времени голосования). status 401)
`curl -X POST -v -d "{"""idMenu""":1,"""idRest""":0,"""counterVoice""":0,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true --user anna2000@mail.com:anneta` (Администратор пытается проголосовать за меню (После истечения времени голосования). status 403)
`curl -X POST -v -d "{"""idMenu""":1,"""idRest""":0,"""counterVoice""":0,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/true --user user@yandex.ru:password` (Обычный пользователь пытается проголосовать за меню (После истечения времени голосования). status 500)

#### забрать голос за меню (Доступно только обычным пользователям)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":3,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false` (Аноним пытается отменить голос за меню (До истечения времени голосования). status 401)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":3,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false --user anna2000@mail.com:anneta` (Администратор пытается отменить голос за меню (До истечения времени голосования). status 403)
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":3,"""date""":"""2021-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false --user stack@yandex.com:Itest` (Обычный пользователь пытается отменить голос за меню (До истечения времени голосования). status 200)

`curl -X POST -v -d "{"""idMenu""":7,"""idRest""":2,"""counterVoice""":1,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false` (Аноним пытается отменить голос за меню (После истечения времени голосования). status 401)
`curl -X POST -v -d "{"""idMenu""":7,"""idRest""":2,"""counterVoice""":1,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false --user anna2000@mail.com:anneta` (Администратор пытается отменить голос за меню (После истечения времени голосования). status 403)
`curl -X POST -v -d "{"""idMenu""":7,"""idRest""":2,"""counterVoice""":1,"""date""":"""2020-07-15""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/vote/update/user/false --user stack@yandex.com:Itest` (Администратор пытается отменить голос за меню (После истечения времени голосования). status 500)