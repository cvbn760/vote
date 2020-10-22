############################################   Пользователи

#### достать пользователя по id
`curl -s GET http://localhost:8080/rest/users/3`

#### достать вообще всех пользователей
`curl -s GET http://localhost:8080/rest/users/all`

#### достать только обычных пользователей
`curl -s GET http://localhost:8080/rest/users/get_us?option=user`

#### достать только администраторов ресторанов
`curl -s GET http://localhost:8080/rest/users/get_us?option=admin`

#### удалить пользователя по id
`curl -X DELETE http://localhost:8080/rest/users/3`

#### получить заготовку пользователя
`curl -s GET http://localhost:8080/rest/users/new_user/1`

#### добавить нового пользователя
`curl -X POST -v -d "{"""id""":null,"""name""":"""Create name""","""email""":"""create_email@yandex.ru""","""password""":"""create pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/users/create`

#### обновить cуществующего пользователя
`curl -X POST -v -d "{"""id""":3,"""name""":"""Update name""","""email""":"""update_email@yandex.ru""","""password""":"""update pswd""","""roles""":["""ADMIN"""]}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/users/create?id=3`

############################################   Меню

#### достать вообще все меню
`curl -s GET http://localhost:8080/rest/menus/all`

#### достать все меню на конкретный день
`curl -s GET http://localhost:8080/rest/menus/all?date=2020-07-14`

#### достать все меню для конкретного ресторана
`curl -s GET http://localhost:8080/rest/menus/all?id=2`

#### достать все меню для конкретного ресторана на конкретный день
`curl -d date=2020-07-14 -d id=2 -G http://localhost:8080/rest/menus/all`

#### достать меню с наибольшим количеством голосов в требуемый день
`curl -s GET http://localhost:8080/rest/menus/get_win/1?date=2020-07-14`

#### достать меню с наименьшим количеством голосов в требуемый день
`curl -s GET http://localhost:8080/rest/menus/get_win/0?date=2020-07-14`

#### получить заготовку меню
`curl -s GET http://localhost:8080/rest/menus/new_menu/1`

#### сохранить меню
`curl -X POST -v -d "{"""idMenu""":null,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/menus/create`
 
#### обновить меню
`curl -X POST -v -d "{"""idMenu""":4,"""date""":"""2020-07-14""","""idRest""":1}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/menus/create`

############################################   Еда

#### достать вообще всю еду
`curl -s GET http://localhost:8080/rest/meals/all`

#### достать еду по id
`curl -s GET http://localhost:8080/rest/meals/3`

#### удалить еду по id
`curl -X DELETE http://localhost:8080/rest/meals/3`

#### достать всю еду на конкретный день
`curl -s GET http://localhost:8080/rest/meals/all?date=2020-07-14`

#### добавить новую еду
`curl -X POST -v -d "{"""mealId""":null,"""menuId""":2,"""name""":"""new_name""","""description""":"""new_description""","""calories""":111,"""price""":99.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/meals/create`

#### обновить существующую еду
`curl -X POST -v -d "{"""mealId""":40,"""menuId""":2,"""name""":"""update_name""","""description""":"""update_description""","""calories""":999,"""price""":11.99}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/meals/create`

############################################   Рестораны

#### достать все рестораны
`curl -s GET http://localhost:8080/rest/rest/all`

#### достать ресторан по id
`curl -s GET http://localhost:8080/rest/rest/1`

#### достать рестораны по id владельца ресторана
`curl -s GET http://localhost:8080/rest/rest/all/2`

#### удалить ресторан по id
`curl -X DELETE http://localhost:8080/rest/rest/3`

#### получить заготовку ресторана
`curl -s GET http://localhost:8080/rest/rest/new_rest`

#### сохранить ресторан
`curl -X POST -v -d "{"""idRest""":null,"""idOwnerRest""":1,"""nameRest""":"""new_name_rest""","""address""":"""new_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/rest/create`

#### обновить ресторан
`curl -X POST -v -d "{"""idRest""":0,"""idOwnerRest""":1,"""nameRest""":"""update_name_rest""","""address""":"""update_rest_address""","""menus""":[],"""adminRest""":{"""id""":1,"""name""":"""Егор""","""email""":"""admin@gmail.com""","""password""":"""admin""","""roles""":["""ADMIN"""]}}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/rest/create`

############################################   Голоса

#### достать вообще все положительные голоса
`curl -s GET http://localhost:8080/rest/vote/get_vote`

#### достать все положительные голоса для конкретного пользователя
`curl -s GET http://localhost:8080/rest/vote/get_vote?id=2`

#### достать все положительные голоса отданные за конкретное меню
`curl -d date=2020-07-14 -d menu_id=0 -G http://localhost:8080/rest/vote/menu`

#### достать голос для конкретного пользователя за конкретный день
`curl -d date=2020-07-15 -d user_id=2 -G http://localhost:8080/rest/vote/user`

#### отдать голос за меню
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":1500,"""date""":"""2020-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/vote/update/2/true`

#### забрать за меню
`curl -X POST -v -d "{"""idMenu""":0,"""idRest""":0,"""counterVoice""":1500,"""date""":"""2020-07-14""","""mealList""":null}" -H "Content-Type: application/json; charset=UTF-8" http://localhost:8080/rest/vote/update/2/false`