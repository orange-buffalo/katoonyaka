module.exports = function (grunt) {

    var saveLicense = require('uglify-save-license');
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-compass');

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        concat: {
            dist: {
                src: [
                    'src/js/third-party/*.js',
                    'src/js/app/controllers/*.js',
                    'src/js/app/directives/*.js',
                    'src/js/app/katoonyaka.js'
                ],
                dest: 'dist/js/katoonyaka.js'
            }
        },

        uglify: {
            options: {
                sourceMap: true,
                sourceMapIncludeSources: true,
                preserveComments: saveLicense
            },
            build: {
                src: 'dist/js/katoonyaka.js',
                dest: 'dist/js/katoonyaka.min.js'
            }
        },

        compass: {
            buildSass: {
                options: {
                    basePath: 'src/sass',
                    config: 'src/sass/.config/config.rb',
                    cssDir: '../../dist/css',
                    sassDir: '.',
                    outputStyle: 'compressed',
                    force: true
                }
            }
        }
    });

    grunt.registerTask('buildJs', ['concat', 'uglify']);
    grunt.registerTask('buildCss', ['compass']);

    grunt.registerTask('default', ['buildJs', 'buildCss']);

};