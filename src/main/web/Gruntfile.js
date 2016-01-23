module.exports = function (grunt) {

    var saveLicense = require("uglify-save-license");
    grunt.loadNpmTasks("grunt-contrib-concat");
    grunt.loadNpmTasks("grunt-contrib-uglify");
    grunt.loadNpmTasks("grunt-contrib-compass");
    grunt.loadNpmTasks('grunt-webfont');

    var version = grunt.option("katoonyakaVersion");

    var jsFileNameBase = "dist/js/katoonyaka." + version;
    var katoonyakaJsFileName = jsFileNameBase + ".js";
    var katoonyakaMinJsFileName = jsFileNameBase + ".min.js";

    grunt.initConfig({
        pkg: grunt.file.readJSON("package.json"),

        concat: {
            dist: {
                src: [
                    "src/js/third-party/*.js",
                    "src/js/app/controllers/*.js",
                    "src/js/app/services/*.js",
                    "src/js/app/directives/*.js",
                    "src/js/app/katoonyaka.js"
                ],
                dest: katoonyakaJsFileName
            }
        },

        uglify: {
            options: {
                sourceMap: true,
                preserveComments: saveLicense
            },
            build: {
                src: katoonyakaJsFileName,
                dest: katoonyakaMinJsFileName
            }
        },

        compass: {
            buildSass: {
                options: {
                    basePath: "src/sass",
                    config: "src/sass/.config/config.rb",
                    cssDir: "../../dist/css",
                    sassDir: ".",
                    outputStyle: "compressed",
                    force: true
                }
            }
        },

        webfont: {
            icons: {
                src: "src/fonts/vectors/*.svg",
                dest: "dist/fonts",
                options: {
                    types: "woff,ttf",
                    embed: "ttf,woff",
                    htmlDemo: false,
                    stylesheet: "scss",
                    template: "src/fonts/templates/_icons-template.scss"
                }
            }
        }
    });

    grunt.registerTask("buildJs", ["concat", "uglify"]);
    grunt.registerTask("buildCss", ["compass"]);
    grunt.registerTask("buildFont", ["webfont"]);

    grunt.registerTask("default", ["buildJs", "buildCss"]);

};