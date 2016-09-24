/*--------------------------------------------- INSERT SCRIPTS -----------------------------------------*/

/*---------------  SEQUENCE ---------------------*/
db.sequence.insert([
	{
	"_id" : "personnel_seq",
	"seq" : 100
	}, 
	{
	"_id" : "order_seq",
	"seq" : 100
	}, 
	{
	"_id" : "table_seq",
	"seq" : 100
	}, 
	{
	"_id" : "table_seating_area_seq",
	"seq" : 100
	}, 
	{
	"_id" : "bill_seq",
	"seq" : 100
	}, 
	{
	"_id" : "tax_details_seq",
	"seq" : 100
	}, 
	{
	"_id" : "category_seq",
	"seq" : 100
	}, 
	{
	"_id" : "item_seq",
	"seq" : 100
	}, 
	{
	"_id" : "sub_category_seq",
	"seq" : 100
	}, 
	{
	"_id" : "message_seq",
	"seq" : 100
	},
	{
    "_id" : "restaurant_info_seq",
    "seq" : 100
	},
	{
    "_id" : "kot_seq",
    "seq" : 100
	}
]);

/*---------------  PERSONNEL ---------------------*/
db.personnel.insert(
	{
	    "_id" : 1,
	    "_class" : "in.cw.sense.api.bo.personnel.entity.Personnel",
	    "pin" : "4c61a70becbc2a160646dc8928b718d02ecf2b980e505c2fe9d82759c177befd",
	    "accessCode" : "e86f78a8a3caf0b60d8e74e5942aa6d86dc150cd3c03338aef25b7d2d7e3acc7",
	    "roleId" : 1,
	    "name" : "ADMIN",
	    "mobile" : "9999999999"
	}
);

/*---------------  CONTACT SUPPORT ---------------------*/
db.contact_support.insert(
	{
		"contact_number" : "+91 999 920 0758", 
		"email" : "support@excuseme.co.in"
	}
);

/*---------------  EMAIL TEMPLATES ---------------------*/
db.email_template.insert([
	{ 
		"_id" : 100, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "PARENT", 
		"value" : "<div style=\"background: #ffffff; font-family: arial, sans-serif;\">\n\t<table class=\"main\" style=\"position: relative\" border=\"0\"\n\t\talign=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tbody>\n\t\t\t<tr>\n\t\t\t\t<td>\n\t\t\t\t\t<div style=\"border: 1px solid #eaeaea; width: 452px; float: left;\">\n\t\t\t\t\t\t<div\n\t\t\t\t\t\t\tstyle=\"width: 100%; margin: auto; padding: 27px 0 30px 0; overflow: hidden;\">\n\t\t\t\t\t\t\t<img src=\"${image-server}/excuseme-logo.png\" width=\"129\"\n\t\t\t\t\t\t\t\theight=\"44\" style=\"float: left; margin: 3px 0 0 150px\"\n\t\t\t\t\t\t\t\talt=\"Excuse Me\"><\/img>\n\t\t\t\t\t\t<\/div>\n\t\t\t\t\t\t<div\n\t\t\t\t\t\t\tstyle=\"background: #f7fcfe; color: #5b5b5b; font-size: 13px; line-height: 17px; clear: both; padding: 35px; float: left; margin-bottom: 10px; width: 84.5%;\">\n\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t\t<tbody>\n\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t<td style=\"width: 48px;\"><img\n\t\t\t\t\t\t\t\t\t\t\t\tsrc=\"${image-server}/bill.png\" style=\"float: left;\"><\/img><\/td>\n\t\t\t\t\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t\t\t\t\t<div\n\t\t\t\t\t\t\t\t\t\t\t\t\tstyle=\"float: left; margin: 2px 0 0 26px; font-size: 22px; font-weight: 100; line-height: 29px\">\n\t\t\t\t\t\t\t\t\t\t\t\t\tYour bill at <span style=\"font-weight: 400;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t${restaurantName} <\/span>\n\t\t\t\t\t\t\t\t\t\t\t\t<\/div>\n\t\t\t\t\t\t\t\t\t\t\t<\/td>\n\t\t\t\t\t\t\t\t\t\t<\/tr>\n\t\t\t\t\t\t\t\t\t<\/tbody>\n\t\t\t\t\t\t\t\t<\/table>\n\t\t\t\t\t\t\t<\/div>\n\n\t\t\t\t\t\t\t${itemList}\n\n\t\t\t\t\t\t<\/div>\n\t\t\t\t\t\t<div style=\"background: #042E6F; height: 33px; clear: both;\">\n\t\t\t\t\t\t\t<p\n\t\t\t\t\t\t\t\tstyle=\"font-size: 20px; padding: 5px 0 20px 0; margin: 0; clear: both; width: 100%; float: left; color: #FFF;\">\n\t\t\t\t\t\t\t\t<span style=\"float: left; width: 45%; text-align: right;\">Grand\n\t\t\t\t\t\t\t\t\tTotal<\/span> <span\n\t\t\t\t\t\t\t\t\tstyle=\"float: left; width: 10%; text-align: center;\">:<\/span> <span\n\t\t\t\t\t\t\t\t\tstyle=\"float: right; width: 45%; text-align: left;\"\n\t\t\t\t\t\t\t\t\tclass=\"valueInfo\">${grandTotal}<\/span>\n\t\t\t\t\t\t\t<\/p>\n\t\t\t\t\t\t<\/div>\n\t\t\t\t\t\t<div style=\"background: #0E2 855; height: 10px;\"><\/div>\n\t\t\t\t\t<\/div>\n\t\t\t\t<\/td>\n\t\t\t<\/tr>\n\t\t\t\n            ${promotion}\n\n\t\t<\/tbody>\n\t<\/table>\n<\/div>\n\n${footer}\n" 
	},
	{ 
		"_id" : 101, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "CATEGORY", 
		"value" : "<div>\n    <p style=\"font-size: 14px; padding: 30px 0 14px 0; margin: 0; clear: both; width: 100%; float: left;\">\n        <span style=\"float: left; font-weight: 200;\">${heading}<\/span>\n    <\/p>\n    <p style=\"font-size: 14px; padding: 5px 0 5px 0; margin: 0; clear: both; width: 100%; float: left; color: #00B0DE;\">\n        <span style=\"float: left; width: 70%;\">Item<\/span>\n        <span style=\"float: left; width: 10%; text-align: left;\">Qty<\/span>\n        <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">Value<\/span>\n    <\/p>\n\n    ${items}\n    ${discount}\n    ${subTotal}\n    ${chargeList}\n    ${totalExclusive}\n    ${taxList}\n    ${totalIncl}\n\n<\/div>" 
	},
	{ 
		"_id" : 102, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "ITEM", 
		"value" : "<p style=\"font-size: 14px; padding: 5px 0 5px 0; margin: 0; clear: both; width: 100%; float: left\">\n    <span style=\"float: left; width: 70%;\">${name}<\/span>\n    <span style=\"float: left; width: 10%; text-align: left;\">${quantity}<\/span>\n    <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">${value}<\/span>\n<\/p>" 
	},
	{ 
		"_id" : 103, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "CHARGE", 
		"value" : "<p style=\"font-size: 14px; padding: 5px 0 5px 0; margin: 0; clear: both; width: 100%; float: left\">\n    <span style=\"float: left; width: 50%;\">${name}<\/span>\n    <span style=\"float: left; width: 30%; text-align: left;\">${rate}<\/span>\n    <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">${value}<\/span>\n<\/p>" 
	},
	{ 
		"_id" : 104, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "SUBTOTAL", 
		"value" : "<p style=\"font-size: 14px; padding: 5px 0 5px 0; margin: 0; clear: both; width: 100%; float: left; color: #00B0DE;\">\n    <span style=\"float: left; width: 70%;\">Subtotal<\/span>\n    <span style=\"float: left; width: 10%; text-align: left;\">${itemCount}<\/span>\n    <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">${value}<\/span>\n<\/p>" 
	},
	{ 
		"_id" : 105, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "TOTAL_EXCLUSIVE", 
		"value" : "<p style=\"font-size: 14px; padding: 5px 0 5px 0; margin: 0; clear: both; width: 100%; float: left\">\n    <span style=\"float: left; width: 70%;\">Total (Exc. Taxes)<\/span>\n    <span style=\"float: left; width: 10%; text-align: left;\"><\/span>\n    <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">${value}<\/span>\n<\/p>" 
	},
	{ 
		"_id" : 106, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "TOTAL_INCLUSIVE", 
		"value" : "<p style=\"font-size: 14px; padding: 5px 0 20px 0; margin: 0; clear: both; \n    border-bottom: 1px solid #ececec; width: 100%; float: left; color: #00B0DE;\">\n    <span style=\"float: left; width: 70%;\">Total (Inc. Taxes)<\/span>\n    <span style=\"float: left; width: 10%; text-align: left;\"><\/span>\n    <span style=\"float: right; width: 20%; text-align: left;\" class=\"valueInfo\">${value}<\/span>\n<\/p>\n" 
	},
	{ 
		"_id" : 107, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "PROMOTION", 
		"value" : "<tr>\n    <td>\n        <table width=\"452\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n            style=\"padding: 30px 0px 15px 0px;\">\n            <tbody>\n                <tr>\n                    <td style=\"font-size: 14px\">\n                        <div style=\"float: left; width: 100%;\" class=\"downInfo\">\n                            <div style=\"width: 100%; display: inline-block;\"\n                                align=\"center\">\n                                <a\n                                    style=\"vertical-align: middle; padding: 0 8% 0 8%; display: inline-block;\"\n                                    target=\"_blank\"> <img src=\"${image-server}/heart.png\"\n                                    width=\"15\" height=\"15\" border=\"0\"><\/img>\n                                <\/a> <span style=\"font-weight: 200;\">Loved the service?\n                                    Show your love...<\/span> <a\n                                    style=\"vertical-align: middle; padding: 0 8% 0 8%; display: inline-block;\"\n                                    target=\"_blank\"> <img src=\"${image-server}/heart.png\"\n                                    width=\"15\" height=\"15\" border=\"0\"><\/img>\n                                <\/a>\n                            <\/div>\n                        <\/div>\n                    <\/td>\n                <\/tr>\n                <tr>\n                    <td>\n                        <div style=\"background: #0E2 855; height: 20px;\"><\/div>\n                    <\/td>\n                <\/tr>\n                <tr>\n                    <td style=\"font-size: 14px\">\n                        <div style=\"float: left; width: 100%;\" class=\"downInfo\">\n                            <div style=\"width: 100%; display: inline-block;\" align=\"center\">\n                                <a href=\"${facebookUrl}\"\n                                    style=\"vertical-align: middle; padding: 0 8% 0 8%; display: inline-block;\"\n                                    target=\"_blank\"> <img src=\"${image-server}/facebook.png\"\n                                    alt=\"Fb\" width=\"40\" height=\"40\" border=\"0\"><\/img>\n                                <\/a> <a href=\"${twitterUrl}\"\n                                    style=\"vertical-align: middle; padding: 0 8% 0 8%; display: inline-block;\"\n                                    target=\"_blank\"> <img src=\"${image-server}/twitter.png\"\n                                    alt=\"Twitter\" width=\"40\" height=\"40\" border=\"0\"><\/img>\n                                <\/a>\n                            <\/div>\n                        <\/div>\n                    <\/td>\n                <\/tr>\n            <\/tbody>\n        <\/table>\n    <\/td>\n<\/tr>" 
	},
	{ 
		"_id" : 108, 
		"_class" : "in.cw.sense.api.bo.emailTemplate.entity.EmailTemplate", 
		"templateType" : "FOOTER", 
		"value" : "<div\n\tstyle=\"border-top: 1px solid #eaeaea; width: 100%; clear: both; margin-top: 45px;\">\n\t<table\n\t\tstyle=\"background: #ffffff; font-family: arial, sans-serif; margin: 0 auto; max-width: 625px; padding: 0; width: 452px;\">\n\t\t<tbody>\n\t\t\t<tr>\n\t\t\t\t<td\n\t\t\t\t\tstyle=\"font-size: 10px; padding: 21px 8px; text-align: center; font-weight: 100\">\n\t\t\t\t\tCopyright &copy; 2016 Excuse Me, all rights reserved.<\/td>\n\t\t\t<\/tr>\n\t\t<\/tbody>\n\t<\/table>\n<\/div>" 
	}
]);