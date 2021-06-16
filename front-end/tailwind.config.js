module.exports = {
	purge: {
		content: ["css/*", "./index.html"],
	},
	darkMode: false, // or 'media' or 'class'
	theme: {
		extend: {},
	},
	variants: {
		extend: {
			backgroundColor: ["active", "focus"],
		},
	},
	plugins: [],
}
