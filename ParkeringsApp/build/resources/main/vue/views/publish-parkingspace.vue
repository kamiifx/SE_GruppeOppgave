<template id="publish-parkingspace" @ParkingSpace-submitted="createParkingSpace">
  <div class="form-style">
    <h2>Create new parkingspace {{user.id}}</h2>
    <form class="create" @submit="checkForm" :action=`/api/app/${user.id}/publish_parkingspace` method="post">
      <div v-if="errors.length">
        <b>Please correct the following error(s):</b>
        <ul>
          <li v-for="error in errors">{{ error }}</li>
        </ul>
      </div>

      <p>
        <label for="city">City<label>
          <input type="text" name="city" id="city" v-model="city">
      </p>

      <p>
        <label for="address">Address<label>
          <input type="text" name="address" id="address" v-model="address">
      </p>

      <p>
        <label for="size_sqm">Size (square meeters)<label>
          <input type="number" name="size_sqm" id="size_sqm" v-model="size_sqm">
      </p>

      <p>
        <label for="price_ph">Price (per hour)<label>
          <input type="number" name="price_ph" id="price_ph" v-model="price_ph">
      </p>

      <p>
        <input type="submit" value="Create Space">
      </p>

    </form>
  </div>

</template>
<script>
Vue.component("publish-parkingspace", {
  template: "#publish-parkingspace",
  data: () => ({
    city: null,
    user: null,
    address: null,
    size_sqm: null,
    price_ph: null,
    errors: []
  }),
  created() {
    const userId = this.$javalin.pathParams["userId"];
    fetch(`/api/users/${userId}`)
      .then(res => res.json())
      .then(res => {
        this.user = res;
      }).catch(() => alert("Error getting user"));
  },
  methods: {
    checkForm: function (e) {
      const urlRegex = "/^(?:(?:(?:https?|ftp):)?\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)(?:\\.(?:[a-z\u00a1-\uffff0-9]-*)*[a-z\u00a1-\uffff0-9]+)*(?:\\.(?:[a-z\u00a1-\uffff]{2,})))(?::\\d{2,5})?(?:[/?#]\\S*)?$/i";
      if (this.city && this.address && this.size_sqm && this.price_ph) return true;
      this.errors = [];
      if (!this.city) this.errors.push("City required");
      if (!this.address) this.errors.push("Address required");
      if (!this.size_sqm) this.errors.push("Size in square meeters required");
      if (!this.price_ph) this.errors.push("Price per hour required");
      e.preventDefault();
    }
  }
});
</script>
<style>
.form-style{
  font-family: 'Open Sans Condensed', arial, sans;
  width: 500px;
  padding: 30px;
  background: #191919;
  margin: 50px auto;
  box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
  -moz-box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.22);
  -webkit-box-shadow:  0px 0px 15px rgba(0, 0, 0, 0.22);
  border: #dddddd;

}
.form-style h2{
  background: #4D4D4D;
  text-transform: uppercase;
  font-family: 'Open Sans Condensed', sans-serif;
  color: #FFFFFF;
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
  color: white;
  border-bottom: 1px solid #ddd;
  background: transparent;
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
  background: none;
  display: inline-block;
  cursor: pointer;
  font-family: 'Open Sans Condensed', sans-serif;
  font-size: 14px;
  text-decoration: none;
  text-transform: uppercase;
  padding: 10px;
  margin: 10px;
  border: 1px solid white;
  color: white;
  border-radius: 15px;
}
.form-style input[type="button"]:hover,
.form-style input[type="submit"]:hover {
  border: 2px solid white;
}

.create {
  color: white;
}
</style>
