class UserService
{
    getAllUser()
    {
         var request = new XMLHttpRequest();
         request.open('GET', '/Diploma/GetAllUser',false);
         request.send();
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             var userList= JSON.parse(request.responseText );
             return userList;
         }
         return null;
    }
    
    getLastId()
    {
         var request = new XMLHttpRequest();
         request.open('GET', '/Diploma/GetLastId',false);
         request.send();
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             var userList= JSON.parse(request.responseText );
             return userList;
         }
         return null;
    }
    
    recognizeUser(json)
    {
         var request = new XMLHttpRequest();
        request.open('POST', '/Diploma/RecognizeUser', false);
        request.setRequestHeader("Content-type", "application/json, charset=UTF-8");
        request.send(json);
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             console.log("All right");
             var login = request.responseText;
             //var user= JSON.parse(request.responseText );
             return login;
         }
         return null;
    }
    getUserById(id)
    {
         var request = new XMLHttpRequest();
         request.open('GET', '/Diploma/GetUserById?id=' + id, false);
         request.send();
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             var user= JSON.parse(request.responseText );
             return user;
         }
         return null;
    }
    deleteUserById(id)
    {
        var request = new XMLHttpRequest();
        request.open('GET', '/Diploma/DeleteUserById?id=' + id, false);
         request.send();
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             var user= JSON.parse(request.responseText );
             return user;
         }
         return null;
    }
    insertUser(json)
    {
        var request = new XMLHttpRequest();
        request.open('POST', '/Diploma/InsertUser',false);
        request.setRequestHeader("Content-type", "application/json, charset=UTF-8");                    
        request.send(json);
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             alert ("All right");
             //var mess = "ready";
             //return  mess;
         }
         return null;
    }
    updateUser(json)
    {
        var request = new XMLHttpRequest();
        request.open('GET', '/Diploma/UpdateUser?json='+json, false);
         request.send();
         if (request.status != 200)  {
              alert( request.status + ': ' + request.statusText ); 
         } 
         else    {
             json= JSON.parse(request.responseText );
             return json;
         }
         return null;
    }
}