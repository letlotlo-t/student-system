const gulp      = require('gulp');
const cleanCSS  = require('gulp-clean-css');
const rename    = require('gulp-rename');

gulp.task('minify-css', function () {
    return gulp.src('src/main/resources/static/css/site.css')
        .pipe(cleanCSS({ compatibility: 'ie8' }))
        .pipe(rename({ suffix: '.min' }))
        .pipe(gulp.dest('src/main/resources/static/css/'));
});

gulp.task('default', gulp.series('minify-css'));