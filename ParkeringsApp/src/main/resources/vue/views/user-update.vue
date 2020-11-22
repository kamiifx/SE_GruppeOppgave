<template id="user-update" @ParkingSpace-submitted="updateUser">
  <div>
    <div id="header">
      <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
      <h3>User update<br> App</h3>
      <div class="logins">
        <button class="button-user-login">Logg ut</button>
      </div>
    </div>
    <div class="form-style">
      <h2>Oppdater brukerinformasjon, {{user.name}}</h2>
      <form class="create" @submit="checkForm" :action=`/api/${user.id}/user-update` method="post">
        <p>
          <label for="userId">User Id<label>
            <input type="text" name="id" id="userId" v-model="userId"  readonly>
        </p>

        <p>
          <label for="email">Epost<label>
            <input type="text" name="email" id="email" v-model="email">
        </p>

        <p>
          <label for="first_name">Fornavn<label>
            <input type="text" name="first_name" id="first_name" v-model="first_name">
        </p>

        <p>
        <label for="last_name">Etternavn<label>
          <input type="text" name="last_name" id="last_name" v-model="last_name">
        </p>

        <p>
          <label for="password">Passord<label>
            <input type="password" name="password" id="password" v-model="password">
        </p>
        <p>
          <input type="submit" value="Oppdater bruker">
        </p>

      </form>
    </div>
  </div>

</template>
<script>
Vue.component("user-update", {
  template: "#user-update",
  data: () => ({
    user : null,
    userId: null,
    email: null,
    first_name: null,
    last_name: null,
    password: null
  }),
  created() {
    const userId = this.$javalin.pathParams["userId"];
    fetch(`/api/users/${userId}`)
        .then(res => res.json())
        .then(res => {
          this.user = res;
          this.userId = res.id;
          this.email = res.mail;
          this.first_name = res.name;
          this.last_name = res.lastName;
          this.password = res.password;

        }).catch(() => alert("Error getting user"));


  },
  methods: {
    checkForm: function (e) {

    }
  }
});
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');
#header img{
  width: 40px;
  margin: 30px;
}
#header{
  display: flex;
  flex-direction: row;
  align-items: center;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 9999;
  -webkit-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
  -moz-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
  box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
}
#header h3{
  font-family: 'Roboto', sans-serif;
  font-weight: 300;
}
#header button{
  font-family: 'Roboto', sans-serif;
  font-size: 17px;
  font-weight: 400;
  color: #222f3e;
  height: 30px;
  border-radius: 5px;
  border-style: none;
}
#header button:hover{
  cursor: pointer;
  transition: 0.5s ease-in-out;
  background-color: #b8e994;
}
.logins{
  margin-left: 84%;
}
.button-user-login{
  margin-left: 15px;
  background-color: #1dd1a1;
}
.form-style{
  position: relative;
  top: 100px;
  font-family: 'Open Sans Condensed', arial, sans;
  width: 500px;
  padding: 30px;
  background: #f1f1f1;
  margin: 50px auto;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
  -moz-box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
  -webkit-box-shadow:  0px 0px 15px rgba(0, 0, 0, 0.22);
  border: #dddddd;

}
.form-style h2{
  background: #ffff;
  text-transform: uppercase;
  font-family: 'Open Sans Condensed', sans-serif;
  color: #4d4d4d;
  font-size: 18px;
  font-weight: 100;
  padding: 20px;
  margin: -30px -30px 30px -30px;
}
.form-style input[type="text"],
.form-style input[type="date"],
.form-style input[type="datetime"],
.form-style input[type="email"],
.form-style input[type="number"],
.form-style input[type="search"],
.form-style input[type="time"],
.form-style input[type="url"],
.form-style input[type="password"],
.form-style textarea,
.form-style select
{
  box-sizing: border-box;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  outline: none;
  display: block;
  width: 100%;
  padding: 7px;
  border: none;
  color: #212121;
  border-bottom: 1px solid #373737;
  background: rgba(229,229,229,0.88);
  margin-bottom: 10px;
  font: 16px Arial, Helvetica, sans-serif;
  height: 45px;
}
.form-style textarea{
  resize:none;
  overflow: hidden;
}
.form-style input[type="button"],
.form-style input[type="submit"]{
  background: #54a0ff;
  border-color: #54a0ff;
  display: block;
  cursor: pointer;
  font-family: 'Open Sans Condensed', sans-serif;
  font-size: 14px;
  text-decoration: none;
  text-transform: uppercase;
  padding: 10px;
  margin: 10px;
  color: white;
  border-radius: 5px;
}
.form-style input[type="button"]:hover,
.form-style input[type="submit"]:hover {
  background-color: #4a69bd;
  border-color: #4a69bd;
}

.create {
  color: #181818;
}
</style>
