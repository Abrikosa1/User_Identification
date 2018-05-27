class UserController
{
    constructor()
    {
        this.userService = new UserService();
    }
    
    // DATAController
    getAllUser()
    {
        return this.userService.getAllUser();
    }
    
    //ViewController
    getAllUserView()
    {
        var data =this.getAllUser();
        var result = "<div>";
        for(var i=0; i<data.length;i++)
        {   
            var id=data[i].id;
            var image=data[i].image;
	result+= "<img style='width: 200px; height: 200px;' class='lib-img-show' src='data:image/png;base64,"+image+"'>"+
                        "</div>";
        } 
        var element =document.getElementById("testing");
        element.innerHTML = result;
    }

    getUserById(id)
    {
        return this.userService.getUserById(id);
    }
    //ViewController
   getUserByIdView(id)  {
       var data=this.getUserById(id);
       var login = data.login;
       var firstName=data.firstName; 
       var lastName=data.lastName;
       var result="<div class='container'>"+
          "<div class='row'>"+
              "<div class='col-md-8 col-md-offset-3'>"+
                  "<h3 class='site-title col-md-offset-1'>Профиль пользователя "+login +"</h3>"+
                  "<div class='row'>"+
                      "<div class='col-md-9'>"+
                          "<div class='row'>"+
                              "<div class='panel'>"+
                                  "<div class='panel-body'>"+
                                      "<div class='col-md-12'>"+
                                          "<img class='img-responsive col-md-offset-5' src='../images/user.png' style='width:15%;'>"+
                                         "<br>"+
                                          "<div class='clearfix'>"+
                                              "<h3 class='col-md-offset-4'>" + firstName + " "+lastName +"</h3>"+
                                              //"<i class='fa fa-wrench'> Edit</i>"+
//                                              "<div class='profile-ratings'>"+
//                                                  "<i class='fa fa-star'></i>"+
//                                                  "<i class='fa fa-star'></i>"+
//                                                  "<i class='fa fa-star'></i>"+
//                                                  "<i class='fa fa-star'></i>"+
//                                                  "<i class='fa fa-star'></i>"+
//                                              "</div>"+
                                              "<h4 style='text-align:center;'>Это ваш аккаунт. Вы можете управлять им, потому что он ваш.</h4>"+
                                              "<button type='button' id='editButton' class='btn btn-success btn-md pull-left' onclick='editClick("+id+")'>Изменить</button>"+
                                              "<button type='button' class='btn btn-danger btn-md pull-center col-md-offset-1' onclick='deleteAccount("+id+")'>Удалить</button>"+
                                              "<button type='button' class='btn btn-xs btn-md pull-right' onclick='exitAccount()'>Выйти</button>"+
                                              "<hr>"+
                                               "<div class='clearfix'>"+
                                               "<div id='editAcc'></div>"+
                                                "</div>"+
                                          "</div>"+
                                      "</div>"+
//                                      "<div class='col-md-7'>"+
//                                          "<div class='profile-block'>"+
//                                          "</div>"+
//                                          "<button type='button' class='btn btn-xs btn-md pull-right pull-down' onclick='exitAccount()'>Выйти</button>"+
//                                      "</div>"+
                                  "</div>"+
                              "</div>"+
                          "</div>"+
                      "</div>"+
                  "</div>"+
              "</div>"+
          "</div>"+
      "</div>";
      var element =document.getElementById("profile");
      element.innerHTML += result;
    }
    
    deleteUserById(id)
    {
        return this.userService.deleteUserById(id);
    }
    deleteUserByIdView(id){
        this.deleteUserById(id);
    }

    insertUser(json)
    {
       return this.userService.insertUser(json);    
    }
    updateUser(json)
    {
       return this.userService.updateUser(json);    
    }
   
}
