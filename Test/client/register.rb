#
#These tests must be run before the login tests
#
require 'rubygems'
require 'selenium-webdriver'
require 'pry'

port = 8081
inputRestricted = true

driver = Selenium::WebDriver.for :firefox
driver.get "localhost:#{port}"

#Switch to register page
switch = driver.find_element(:link, "Click Here")
switch.click

puts "********** Register **********"
puts "***** Nine Tests To Pass *****"

##################### Try registering without input ####################
button = driver.find_element(:tag_name, "button")
button.click

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "(1) Passed - Did not register without input"
else
  puts "(1) FAILED - Registered without input"
  driver.quit
end

##################### Try putting in a TON of text into text fields without newlines ####################
form = driver.find_elements(:class, "form-control") #Have to change this test to deal with truncation
username = form[0]
password = form[1]
email = form[2]

userIn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
passIn = "pass1"
emailIn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaa.a"

emailLength = emailIn.length

if(inputRestricted)
  username.send_keys userIn
  password.send_keys passIn
  email.send_keys emailIn

  if username["value"].length <= 25 && email["value"].length == emailLength
    puts "(2) Passed - Prevented input of more than 25 characters, but preserved email"
  else
    puts "(2) FAILED - Allowed input with more than 25 characters, or did not preserve email"
    #driver.quit
  end
end

##################### Try registering with a bad username ####################
username.clear
password.clear
email.clear

username.send_keys "àë~çœū"
password.send_keys "password"
email.send_keys "a@email.com"

button.click

#These are place holders for when the real server messages come back
#The user should receive an error message of some kind
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "(3) Passed - Did not register with bad username"
else
  puts "(3) FAILED - Registered with bad username"
  driver.quit
end

##################### Try registering with bad password - bad characters ####################
username.clear
password.clear
email.clear

username.send_keys "username"
password.send_keys "àë~çœū"
email.send_keys "a@email.com"

button.click

#These are place holders for when the real server messages come back
#The user should receive an error message of some kind
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError


if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "(4) Passed - Did not register with bad password (characters)"
else
  puts "(4) FAILED - Registered with bad password (characters)"
  driver.quit
end

##################### Try registering with bad password - too short ####################
username.clear
password.clear
email.clear

username.send_keys "shortpassword"
password.send_keys "a"
email.send_keys "a@email.com"

button.click

#These are place holders for when the real server messages come back
#The user should receive an error message of some kind
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError


if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "(5) Passed - Did not register with bad password (not too short)"
else
  puts "(5) FAILED - Registered with bad password (too short)"
  driver.quit
end

##################### Try registering with bad password - too long ####################
if(!inputRestricted) #Have to change this test to deal with truncation
  username.clear
  password.clear
  email.clear

  username.send_keys "longpassword"
  password.send_keys "lkj6789yuioghjkvbnmghjkyuioasdfcvbnyuio"
  email.send_keys "a@email.com"

  button.click

  #These are place holders for when the real server messages come back
  #The user should receive an error message of some kind
  driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
  driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

  if driver.current_url == "http://localhost:#{port}/\#/register"
    puts "(6) Passed - Did not register with bad password (not too long)"
  else
    puts "(6) FAILED - Registered with bad password (too long)"
    driver.quit
  end
end
##################### Try registering with bad email ####################
username.clear
password.clear
email.clear

userIn = "bademail"
passIn = "password"
emailIn = "email"

username.send_keys userIn
password.send_keys passIn
email.send_keys emailIn

button.click

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "(7) Passed - Did not register with invalid email"
else
  puts "(7) FAILED - Registered with invalid email"
  driver.quit
end

##################### Try registering with good username/password ####################
username.clear
password.clear
email.clear

username.send_keys "gooduser"
password.send_keys "password"
email.send_keys "user@email.com"

button.click

#These are place holders for when the real server messages come back
#The user should be taken to the Game Lobby page at this point
#driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

wait = Selenium::WebDriver::Wait.new(:timeout => 1)
wait.until { driver.current_url == "http://localhost:#{port}/\#/gameLobby" }

if driver.current_url == "http://localhost:#{port}/\#/gameLobby"
  puts "(8) Passed - Registered with valid credentials"
else
  puts "(8) FAILED - Register failed with valid input"
  driver.quit
end

##################### Try registering with an existing username ####################

#Logout of game lobby
logout = driver.find_element(:css, "button.navbar-link")
logout.click

#Move to register page
switch = driver.find_element(:link, "Click Here")
switch.click

#Register with existing username
form = driver.find_elements(:class, "form-control")
username = form[0]
password = form[1]
email = form[2]

username.send_keys "gooduser"
password.send_keys "otherpassword"
email.send_keys "other@email.com"

button = driver.find_element(:tag_name, "button")
button.click

driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

if driver.current_url == "http://localhost:#{port}/\#/register"
 puts "(9) Passed - Did not register with used username"
else
 puts "(9) FAILED - Registered with used username"
end

driver.quit
