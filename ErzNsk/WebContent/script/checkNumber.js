function checkNumber(e)
{
  var e = e || window.event;
  if(e.ctrlKey||e.altKey)return;
  var c = e.charCode || e.keyCode;
  return ((c != 60) && (c != 62));
 
}