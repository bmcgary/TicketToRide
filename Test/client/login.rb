#
#These login tests must be run after the register tests
#
require 'rubygems'
require 'selenium-webdriver'
require 'pry'

port = 8081

driver = Selenium::WebDriver.for :firefox
driver.get "localhost:#{port}"

puts "********** Login ***********"
puts "***** Six Tests To Pass *****"

##################### Try logging in without input ####################
button = driver.find_element(:tag_name, "button")
button.click

if driver.current_url == "http://localhost:#{port}/\#/login"
  puts "(1) Passed - Did not login without input"
else
  puts "(1) FAILED - Logged in without input"
end

##################### Try logging in with unregistered username ####################
form = driver.find_elements(:class, "form-control")
username = form[0]
password = form[1]

userIn = "notAUsername"
passIn = "password"

username.send_keys userIn
password.send_keys passIn

button.click

#These are place holders for when the real server messages come back
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError


if driver.current_url == "http://localhost:#{port}/\#/login"
  puts "(2) Passed - Did not login with unregistered username"
else
  puts "(2) FAILED - Logged in with unregistered username"
end

##################### Try logging in with unregistered password ####################
form = driver.find_elements(:class, "form-control")
username = form[0]
password = form[1]

userIn = "gooduser"
passIn = "NOTaPassword"

username.send_keys userIn
password.send_keys passIn

button.click

#These are place holders for when the real server messages come back
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError


if driver.current_url == "http://localhost:#{port}/\#/login"
 puts "(3) Passed - Did not login with wrong password"
else
 puts "(3) FAILED - Logged in with wrong password"
end

##################### Try putting in a TON of text into text fields without newlines ####################
username.clear
password.clear

userIn = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
passIn = "pass1"
username.send_keys userIn
password.send_keys passIn

if username["value"].length == 25 && password["value"].length == passIn.length
  puts "(4) Passed - Prevented input of more than 25 characters"
else
  puts "(4) FAILED - Allowed input with more than 25 characters"
end

##################### Try putting in text with newlines into text fields ####################
username.clear
password.clear

username.send_keys "hello from the other side
I must have called a thousand times
To tell you I'm sorry
For everything that I've done"
#These are place holders so that the test can keep running
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError
driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError

password.send_keys "Adele"

if driver.current_url == "http://localhost:#{port}/\#/login"
    puts "(5) Passed - Did not login with newline input"
else
    puts "(5) FAILED - Logged in with newline input"
end

##################### Try logging in with good username/password ####################
username.clear
password.clear

username.send_keys "gooduser"
password.send_keys "password"

button.click

driver.switch_to.alert.accept rescue Selenium::WebDriver::Error::NoAlertOpenError


wait = Selenium::WebDriver::Wait.new(:timeout => 1)
wait.until { driver.current_url == "http://localhost:#{port}/\#/gameLobby" }

if driver.current_url == "http://localhost:#{port}/\#/gameLobby"
  puts "(6) Passed - Logged in with valid credentials"
else
  puts "(6) FAILED - Log in failed with valid input"
end

#Logout of game lobby
logout = driver.find_element(:css, "button.navbar-link")
logout.click

driver.quit
