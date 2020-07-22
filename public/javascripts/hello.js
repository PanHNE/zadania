let flagFirstLevel = true;
let flagSecondLevel = true;

/**
 * Funkcja do pokazywani i ukrywania drugiego poziomu
 */

$('.first-level').on('click', function(){
  const child = $(this).children('.second-level')
  if (flagFirstLevel) {
    child.show()
  } else {
    child.hide()
  }
  flagFirstLevel = !flagFirstLevel;
})

/**
 * Funkcja do pokazywani i ukrywania trzeciego poziomu
 */
$('.second-level').on('click', function(){
  const child = $(this).children('.third-level')
  if (flagSecondLevel) {
    child.show()
  } else {
    child.hide()
  }
  flagFirstLevel = !flagFirstLevel;
  flagSecondLevel = !flagSecondLevel
})