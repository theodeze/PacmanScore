/**
 * 
 */
Vue.use(VeeValidate);

var show_tab = new Vue({
	el: '#app-tab',
	data:{
		id_gen:dropdownMenuButton_classement,
		id_perso:dropdownMenuButton_personnel,
		v1: true,
		v2: false
	},
	
	methods:{
		show_gen:function(){
			this.v1 = true;
			this.v2 = false;
		},
		show_perso:function(){
			this.v1 = false;
			this.v2 = true;
		}
	}
});

var show_connexion = new Vue({
	el: '#app-co',
	data:{
		v1: true,
		v2: false
	},
	
	methods:{
		connexion:function(){
			this.v1 = true;
			this.v2 = false;
		},
		deconnexion:function(){
			this.v1 = false;
			this.v2 = true;
		}
	}
});

var vueMDP = new Vue({
  el: '#app-user',
  methods: {
    validateBeforeSubmit() {
      this.$validator
        .validateAll()
        .then(function(response) {
          // Validation success if response === true
        })
        .catch(function(e) {
          // Catch errors
        })
    }
  }
});


var vueAccount = new Vue({
	el: '#app-account',
	data:{
		v1_account: false,
		v2_account: false
	},	
	methods:{
		creat:function(){
			this.v1_account = true;
			this.v2_account = false;
		},
		conn:function(){
			this.v1_account = false;
			this.v2_account = true;
		}
	}	
});