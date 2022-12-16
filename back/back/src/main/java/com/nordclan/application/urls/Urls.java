package com.nordclan.application.urls;

public interface Urls {
    String SERVICE_NAME = "app";
    String ROOT = Api.NAME + "/" + SERVICE_NAME;


    interface Api {
        String NAME = "api";
    }


    interface Purchase {
        String PART = "purchase";
        String FULL = ROOT + "/" + PART;
    }

    interface Refund {
        String PART = "refund";
        String FULL = ROOT + "/" + PART;
    }

    interface CurrentLevel {
        String PART = "currentLevel";
        String FULL = ROOT + "/" + PART;

        interface CardNumber {
            String PART = "{cardNumber}";
            String FULL = CurrentLevel.FULL + "/" + PART;
        }
    }

    interface Discount {
        String PART = "discount";
        String FULL = ROOT + "/" + PART;

        interface CardNumber {
            String PART = "{cardNumber}";
            String FULL = Discount.FULL + "/" + PART;

            interface Date {
                String PART = "{date}";
                String FULL = Discount.CardNumber.FULL + "/" + PART;
            }
        }
    }

    interface Card {
        String PART = "card";
        String FULL = ROOT + "/" + PART;

        interface CardNumber {
            String PART = "{cardNumber}";
            String FULL = Card.FULL + "/" + PART;
        }
    }

    interface Level {
        String PART = "level";
        String FULL = ROOT + "/" + PART;

        interface Id {
            String PART = "{id}";
            String FULL = Level.FULL + "/" + PART;
        }
    }

}

