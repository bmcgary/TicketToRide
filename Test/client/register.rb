#
#These tests must be run before the login tests
#
require 'rubygems'
require 'selenium-webdriver'
require 'pry'

port = 8081
inputRestricted = false

driver = Selenium::WebDriver.for :firefox
driver.get "localhost:#{port}"

#Switch to register page
switch = driver.find_element(:link, "Click Here")
switch.click

##################### Try registering without input ####################
button = driver.find_element(:tag_name, "button")
button.click

if driver.current_url == "http://localhost:#{port}/\#/register"
  puts "Passed - Did not register without input"
else
  puts "FAILED - Registered in without input"
end

##################### Try putting in a TON of text into text fields without newlines ####################
  form = driver.find_elements(:class, "form-control")
  username = form[0]
  password = form[1]
  email = form[2]

  userIn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
  passIn = "pass1"
  emailIn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@aaaaaaaaa.a"

if(inputRestricted)
  username.send_keys userIn
  password.send_keys passIn
  email.send_keys emailIn

  if username["value"].length <= 25 && email["value"].length == email.length
    puts "Passed - Prevented input of more than 25 characters, but preserved email"
  else
    puts "FAILED - Allowed input with more than 25 characters"
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
  puts "Passed - Did not register with bad username"
else
  puts "FAILED - Registered in with bad username"
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
  puts "Passed - Did not register with bad password (characters)"
else
  puts "FAILED - Registered in with bad password (characters)"
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
  puts "Passed - Did not register with bad password (not too short)"
else
  puts "FAILED - Registered in with bad password (too short)"
end

##################### Try registering with bad password - too long ####################
if(inputRestricted)
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
    puts "Passed - Did not register with bad password (not too long)"
  else
    puts "FAILED - Registered in with bad password (too long)"
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
  puts "Passed - Did not register with invalid email"
else
  puts "FAILED - Registered in with invalid email"
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
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

if driver.current_url == "http://localhost:#{port}/\#/gameLobby"
  puts "Passed - Registered with valid credentials"
else
  puts "FAILED - Register failed with valid input"
end

##################### Try registering with an existing username ####################

#Logout of game lobby

# form = driver.find_elements(:class, "form-control")
# username = form[0]
# password = form[1]
# email = form[2]
#
# username.send_keys "user1"
# password.send_keys "otherpassword"
# email.send_keys "other@email.com"
#
# button = driver.find_element(:tag_name, "button")
# button.click
#
# if driver.current_url == "http://localhost:#{port}/\#/register"
#   puts "Passed - Did not register with used username"
# else
#   puts "FAILED - Registered with used username"
# end

driver.quit
