Vue.use(VeeValidate, { locale: 'fr' });

var main = new Vue({
	el: '#main',
	data: {
		v1_account: false,
		v2_account: false,
		v1_tab: true,
		v2_tab: false,
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
		fermer: function() {
			this.v_modif = false;
			this.v1_account = false;
			this.v2_account = false;
		}		
	}
});

