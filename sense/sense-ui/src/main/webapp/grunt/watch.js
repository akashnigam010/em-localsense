'use strict';

module.exports = {
 scripts: {
    files: [
    'src/app/**/*.js',
    'src/app/app.js',
    'grunt/*.js',
    ],
    options: {
      spawn: false,
      livereload:true
    },
  },
   html: {
    files: [
     'src/app/**/*.html',
     'src/app/index.html'],
    options: {
      spawn: false,	
      livereload: true
    }
  },
};