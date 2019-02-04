
Vue.use(VeeValidate, { locale: 'fr' });

var main = new Vue({
	el: '#main',
	data: {
		v1_account: false,
		v2_account: false,
		v1_tab: true,
		v2_tab: false,
		v1_connexion: true,
		v2_connexion: false,
		v_modif: false
	},
	methods: {
		show_gen: function () {
			this.v1_tab = true;
			this.v2_tab = false;
		},
		show_perso: function () {
			this.v1_tab = false;
			this.v2_tab = true;
		},
		connexion: function () {
			this.v1_connexion = false;
			this.v2_connexion = true;
		},
		deconnexion: function () {
			this.v1_connexion = true;
			this.v2_connexion = false;
			this.v_modif = false;
			this.v1_tab = true;
			this.v2_tab = false;
		},
		validateBeforeSubmit() {
			this.$validator.localize('fr');
			this.$validator
				.validateAll()
				.then(function (response) {
					// Validation success if response === true
				})
				.catch(function (e) {
					// Catch errors
				})
		},
		create: function () {
			this.v1_account = true;
			this.v2_account = false;
		},
		notConnected: function () {
			this.v1_account = false;
			this.v2_account = true;
		},
		modification: function () {
			this.v_modif = true;
		},
		fermer_modification: function() {
			this.v_modif = false;
		}
		
	}
});
