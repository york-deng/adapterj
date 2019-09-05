/**
 * Copyright (c) 2019, ADAPTERJ. All rights reserved.
 * Code licensed under the BSD License:
 * http://developer.adapterj.com/adapterj/license.txt
 * Version: 0.11.4
 */

String.prototype.replaceAll = function(target, replacement) {
    return this.split(target).join(replacement);
};

/**
 * The ADAPTERJ object is the single global object used by ADAPTERJ Library.  It
 * contains utility function for setting up namespaces, inheritance, and
 * logging.  ADAPTERJ.util, ADAPTERJ.widget, and ADAPTERJ.example are namespaces
 * created automatically for and used by the library.
 * 
 * @module ADAPTERJ
 */

/**
 * The ADAPTERJ global namespace object
 * 
 * @class ADAPTERJ
 * @static
 */
if (typeof ADAPTERJ == "undefined") {
    ADAPTERJ = { };
}

/**
 * Returns the namespace specified and creates it if it doesn't exist
 *
 * ADAPTERJ.namespace("property.package");
 * ADAPTERJ.namespace("ADAPTERJ.property.package");
 *
 * Either of the above would create ADAPTERJ.property, then
 * ADAPTERJ.property.package
 *
 * Be careful when naming packages. Reserved words may work in some browsers
 * and not others. For instance, the following will fail in Safari:
 *
 * ADAPTERJ.namespace("really.long.nested.namespace");
 *
 * This fails because "long" is a future reserved word in ECMAScript
 * 
 * @method namespace
 * @static
 * @param  {String} ns The name of the namespace
 * @return {Object}    A reference to the namespace object
 */
ADAPTERJ.namespace = function(ns) {

    if (!ns || !ns.length) {
        return null;
    }

    var levels = ns.split(".");
    var nsObj = ADAPTERJ;

    // ADAPTERJ is implied, so it is ignored if it is included
    for (var i = (levels[0] == "ADAPTERJ") ? 1 : 0; i < levels.length; ++ i) {
        nsObj[levels[i]] = nsObj[levels[i]] || {};
        nsObj = nsObj[levels[i]];
    }

    return nsObj;
};

ADAPTERJ.namespace("widget");

/**
 * Link
 */
ADAPTERJ.widget.Link = function(url, text) {
    this.init(url, text);
};

ADAPTERJ.widget.Link.prototype = {
    
    /**
     *
     * @type String
     */
    _id: "link",
        
    /**
     *
     * @type String
     */
    _url: undefined,
        
    /**
     *
     * @type String
     */
    _text: undefined,
        
    /**
     * Initializes the Link object
     *
     * @parm length 
     * @private
     */
    init: function(url, text) {
        this._url = url;
        this._text = text;
    },

    setId: function(id) {
        this._id = id;
    },
    
    getId: function() {
        return this._id;
    },
    
    setURL: function(url) {
        this._url = url;
    },
    
    getURL: function() {
        return this._url;
    },
    
    setText: function(text) {
        this._text = text;
    },
    
    getText: function() {
        return this._text;
    },
    
    toString: function() {
        return "Link{ text:" + this._text + ", url:" + this._url + " }";
    }
};

/**
 * LinkGroup
 */
ADAPTERJ.widget.LinkGroup = function(length) {
    this.init(length);
};

ADAPTERJ.widget.LinkGroup.prototype = {
    
    /**
     *
     *@type int
     */
    _length: 0,
    
    /**
     *
     * @type Text[]
     */
    _links: undefined,
    
    /**
     * Initializes the LinkGroup object 
     *
     * @parm length 
     * @private
     */
    init: function(length) {
        this._length = length;
        this._links  = new Array(length);
    },

    length: function() {
        return this._links.length;
    },
    
    setText: function(index, text) {
        this._links[index] = text;
    },
        
    getText: function(index) {
        return this._links[index];
    },
    
    toString: function() {
        return "LinkGroup{ length:" + this._length + " }";
    }
};

/**
 * Text
 */
ADAPTERJ.widget.Text = function(text) {
    this.init(text);
};

ADAPTERJ.widget.Text.prototype = {
    
    /**
     *
     * @type String
     */
    _id: "text",
    
    /**
     *
     * @type String
     */
    _text: undefined,
        
    /**
     * Initializes the Text object
     *
     * @parm length 
     * @private
     */
    init: function(text) {
        this._text = text;
    },

    setId: function(id) {
        this._id = id;
    },
    
    getId: function() {
        return this._id;
    },
    
    setText: function(text) {
        this._text = text;
    },
    
    getText: function() {
        return this._text;
    },
    
    toString: function() {
        return "Text{ text:" + this._text + " }";
    }
};

/**
 * TextGroup
 */
ADAPTERJ.widget.TextGroup = function(length) {
    this.init(length);
};

ADAPTERJ.widget.TextGroup.prototype = {
    
    /**
     *
     *@type int
     */
    _length: 0,
    
    /**
     *
     * @type Text[]
     */
    _texts: undefined,
    
    /**
     * Initializes the TextGroup object 
     *
     * @parm length 
     * @private
     */
    init: function(length) {
        this._length = length;
        this._texts  = new Array(length);
    },

    length: function() {
        return this._texts.length;
    },
    
    setText: function(index, text) {
        this._texts[index] = text;
    },
        
    getText: function(index) {
        return this._texts[index];
    },
    
    toString: function() {
        return "TextGroup{ length:" + this._length + " }";
    }
};

/**
 * SelectOptions
 */
ADAPTERJ.widget.SelectOptions = function() { };

ADAPTERJ.widget.SelectOptions.prototype = {

    /**
     *
     * @type { String:String }
     */
    _options: { },

    /**
     *
     * @type String
     */
    _help: "&lt;Please select from the options below&gt;",

    /**
     *
     * @type String
     */
    _placeholderForEmpty: "N/A",

    /**
     *
     * @type String
     */
    _selected: null,

    setHelp: function(help) {
        this._help = help;
    },
    
    setPlaceholderForEmpty: function(placeholder) {
        this._placeholderForEmpty = placeholder;
    },
    
    put: function(value, text) {
        var options = new Object();
        const keys = Object.keys(this._options);
        for (var i = 0; i < keys.length; i ++) { options[keys[i]] = this._options[keys[i]]; };
        options[value] = text;
        
        this._options = options;
    },

    isEmpty: function() {
        return this._options.length == 0;
    },

    selected: function(value) {
        if (value != null && value != undefined) this._selected = value;
        var text = null;
        if (this._selected != null && this._selected != undefined && this._selected != "") {
            text = this._options[this._selected];
        }
        return (text) ? text : this._placeholderForEmpty;
    },

    getPlaceholderForEmpty: function() {
        return this._placeholderForEmpty;
    },
    
    toString: function() {
        return "SelectOptions{ }";
    }
};

/**
 * SimpleSelectOptions
 */
ADAPTERJ.widget.SimpleSelectOptions = function(selectId, options, selected) {
    ADAPTERJ.widget.SelectOptions.call(this);
    
    this._id = selectId;
    this._selected = selected;
    
    const keys =  Object.keys(options);
    for (var i = 0; i < keys.length; i ++) {
        this.put(keys[i], options[keys[i]]);
    }
};

ADAPTERJ.widget.SimpleSelectOptions.prototype = new ADAPTERJ.widget.SelectOptions();

ADAPTERJ.widget.SimpleSelectOptions.prototype.toHTMLString = function() {
    var s = "<select id=\"" + this._id + "\" name=\"" + this._id + "\" >";
    
    if (this._help != null && this._help != undefined && this._help != "") {
        s = s + "<option value=\"null\">" + (this._help) + "</option>";
    }
    
    const keys = Object.keys(this._options);
    for (var i = 0; i < keys.length; i ++) {
        var key = keys[i];
        var text = this._options[key];
        s = s + "<option value=\"" + key + "\"";
        if (this._selected != null && this._selected != undefined && this._selected == key) {
            s = s + " selected";
        };
        s = s + ">" + text + "</option>";
    }
    s = s + "</select>";
    
    return (s);
}

ADAPTERJ.widget.SimpleSelectOptions.prototype.toString = function() {
    return "SimpleSelectOptions{ id:" + this._id + " }";
}

/**
 * Adapter
 */
ADAPTERJ.widget.Adapter = function(id) {
    if (id) { this.init(id); }
};

ADAPTERJ.widget.Adapter.prototype = {

    /**
     *
     * @type String
     */
    _id: undefined,

    /**
     * Initializes the Adapter object
     *
     * @parm id 
     * @private
     */
    init: function(id) {
        this._id = id;
    },

    toString: function() {
        return "Adapter{ id:" + this._id + " }";
    }
}

/**
 * POJOAdapter
 */
ADAPTERJ.widget.POJOAdapter = function(id) {
    if (id) {
        ADAPTERJ.widget.Adapter.call(this, id);
    }
    
    this._data  = undefined;
    this._links = undefined;
    this._placeholder = ("");
};

ADAPTERJ.widget.POJOAdapter.prototype = new ADAPTERJ.widget.Adapter();

ADAPTERJ.widget.POJOAdapter.prototype.setData = function(data) { this._data = data; };

ADAPTERJ.widget.POJOAdapter.prototype.setLinkGroup = function(links) {
    if (!(links instanceof ADAPTERJ.widget.LinkGroup)) {
        throw new TypeError("links MUST be an instance of LinkGroup");
    }
    this._links = links;
};

ADAPTERJ.widget.POJOAdapter.prototype.setPlaceholderForNull = function(placeholder) {
    if (!(placeholder instanceof String)) {
        throw new TypeError("placeholder MUST be an instance of String");
    }
    this._placeholder = placeholder;
};

ADAPTERJ.widget.POJOAdapter.prototype.toString = function() {
    return "POJOAdapter{ id:" + this._id + " }";
}

/**
 * SimpleViewAdapter
 */
ADAPTERJ.widget.SimpleViewAdapter = function(id) {
    if (id) {
        ADAPTERJ.widget.POJOAdapter.call(this, id);
    }
    
    this._optionsMap = { };
};

ADAPTERJ.widget.SimpleViewAdapter.prototype = new ADAPTERJ.widget.POJOAdapter();

ADAPTERJ.widget.SimpleViewAdapter.prototype.putSelectOptions = function(id, options) {
    this._optionsMap[id] = options;
};

ADAPTERJ.widget.SimpleViewAdapter.prototype.getSelectOptions = function(id) {
    return this._optionsMap[id];
}

ADAPTERJ.widget.SimpleViewAdapter.prototype.bindViewHolder = function() {
    if (!(this._data)) {
        // 清除模板中的演示数据
    } else {
        var valueMap = { };
        
        const nameArray = Object.keys(this._data);
        for (var i = 0; i < nameArray.length; i ++) {
            var name = nameArray[i];
            var id = this._id + "." + name;
            var value = this._data[name];
            
            var options = this.getSelectOptions(name);
            if (options) {
                value = options.selected(value);
            }
            
            valueMap[id] = value;
        }
        
        if (this._links) {
            for (var i = 0; i < this._links.length(); i ++) {
                var link = this._links.getLink(i);
                var id = link.getId() + "[" + i + "]";
                
                valueMap[id + ".text"] = link.getText();
                valueMap[id + ".url"] = link.getURL();
            }
        }
        
        const idArray = Object.keys(valueMap);
        for (var i = 0; i < idArray.length; i ++) {
            var id = idArray[i];
            var element = document.getElementById(id);
            
            if (element) {
                if (element.tagName == "A") {
                    element.href = valueMap[id];
                    if (element.innerHTML == "") element.innerHTML = valueMap[id];
                } else if (element.tagName == "IMG") {
                    element.src = valueMap[id];
                } else if (element.tagName == "INPUT") {
                    element.value = valueMap[id];
                } else {
                    element.innerHTML = valueMap[id];
                }
            }
        }
    }
};

ADAPTERJ.widget.SimpleViewAdapter.prototype.toString = function() {
    return "SimpleViewAdapter{ id:" + this._id + " }";
}

/**
 * SimpleFormAdapter
 */
ADAPTERJ.widget.SimpleFormAdapter = function(id) {
    if (id) {
        ADAPTERJ.widget.POJOAdapter.call(this, id);
    }
    
    this._optionsMap = { };
};

ADAPTERJ.widget.SimpleFormAdapter.prototype = new ADAPTERJ.widget.POJOAdapter();

ADAPTERJ.widget.SimpleFormAdapter.prototype.putSelectOptions = function(id, options) {
    this._optionsMap[id] = options;
};

ADAPTERJ.widget.SimpleFormAdapter.prototype.getSelectOptions = function(id) {
    return this._optionsMap[id];
}

ADAPTERJ.widget.SimpleFormAdapter.prototype.bindViewHolder = function() {
    if (!(this._data)) {
        // 清除模板中的演示数据
    } else {
        var valueMap = { };
        
        const nameArray = Object.keys(this._data);
        for (var i = 0; i < nameArray.length; i ++) {
            var name = nameArray[i];
            var id = this._id + "." + name;
            var value = this._data[name];
            
            var options = this.getSelectOptions(name);
            if (options) {
                options.selected(value);
                
                value = options;
            }
            
            valueMap[id] = value;
        }
        
        if (this._links) {
            for (var i = 0; i < this._links.length(); i ++) {
                var link = this._links.getLink(i);
                var id = link.getId() + "[" + i + "]";
                
                valueMap[id + ".text"] = link.getText();
                valueMap[id + ".url"] = link.getURL();
            }
        }
        
        const idArray = Object.keys(valueMap);
        for (var i = 0; i < idArray.length; i ++) {
            var id = idArray[i];
            var element = document.getElementById(id);
            
            if (element) {
                if (element.tagName == "A") {
                    element.href = valueMap[id];
                    if (element.innerHTML == "") element.innerHTML = valueMap[id];
                } else if (element.tagName == "IMG") {
                    element.src = valueMap[id];
                } else if (element.tagName == "INPUT") {
                    if (element.hasAttribute("TYPE")) {
                        if (element.type == "TEXT") {
                            element.value = valueMap[id];
                        } else if (element.type == "CHECKBOX") {
                            if (valueMap[id] == "true") {
                                element.setAttribute("CHECKED", "checked");
                            }
                            element.value = "true";
                        } else if (element.type == "RADIO") {
                            if (valueMap[id] == "true") {
                                element.setAttribute("CHECKED", "checked");
                            }
                            element.value = "true";
                        } else {
                            element.value = valueMap[id];
                        }
                    }
                } else if (element.tagName == "SELECT") {
                    var options = valueMap[id];
                    if (options instanceof ADAPTERJ.widget.SelectOptions) {
                        element.innerHTML = options.toHTMLString();
                    }
                } else {
                    element.innerHTML = valueMap[id];
                }
            }
        }
    }
};

ADAPTERJ.widget.SimpleFormAdapter.prototype.toString = function() {
    return "SimpleFormAdapter{ id:" + this._id + " }";
}

/**
 * ListAdapter 
 */
ADAPTERJ.widget.ListAdapter = function(id) {
    if (id) {
        ADAPTERJ.widget.Adapter.call(this, id);
    }
    this._classId = "_list";
    this._entryId = "entry";
    this._indexId = "i";
    
    this._data  = [ ];
    this._linksArray = [ ];
    this._placeholderForNull = ("");
    this._placeholderForEmpty = ("");
};

ADAPTERJ.widget.ListAdapter.prototype = new ADAPTERJ.widget.Adapter();

ADAPTERJ.widget.ListAdapter.prototype.addItem = function(pojo) {
    this._data[this._data.length] = pojo;
};

ADAPTERJ.widget.ListAdapter.prototype.addAllItems = function(data) {
    for (var i = 0; i < data.length; i ++) { this._data[this._data.length] = data[i]; };
};

ADAPTERJ.widget.ListAdapter.prototype.clear = function(data) { this._data = [ ]; };

ADAPTERJ.widget.ListAdapter.prototype.addLinkGroup = function(links) {
    if (!(links instanceof ADAPTERJ.widget.LinkGroup)) {
        throw new TypeError("links MUST be an instance of LinkGroup");
    }
    
    var length = this._linksArray.length;
    var linksArray = new Array(length + 1);
    for (var i = 0; i < this._linksArray.length; i ++) { linksArray[i] = this._linksArray[i]; };
    linksArray[length] = links;
    
    this._linksArray = linksArray;
};

ADAPTERJ.widget.ListAdapter.prototype.setPlaceholderForNull = function(placeholder) {
    if (!(placeholder instanceof String)) {
        throw new TypeError("placeholder MUST be an instance of String");
    }
    this._placeholderForNull = placeholder;
};

ADAPTERJ.widget.ListAdapter.prototype.setPlaceholderForEmpty = function(placeholder) {
    if (!(placeholder instanceof String)) {
        throw new TypeError("placeholder MUST be an instance of String");
    }
    this._placeholderForEmpty = placeholder;
};

ADAPTERJ.widget.ListAdapter.prototype.toString = function() {
    return "ListAdapter{ id:" + this._id + " }";
}

/**
 * SimpleListAdapter
 */
ADAPTERJ.widget.SimpleListAdapter = function(id) {
    if (id) {
        ADAPTERJ.widget.ListAdapter.call(this, id);
    }
    this._classId = "_list";
    this._entryId = "item";
    this._indexId = "j";
    
    this._optionsMap = { };
};

ADAPTERJ.widget.SimpleListAdapter.prototype = new ADAPTERJ.widget.ListAdapter();

ADAPTERJ.widget.SimpleListAdapter.prototype.putSelectOptions = function(id, options) {
    this._optionsMap[id] = options;
};

ADAPTERJ.widget.SimpleListAdapter.prototype.getSelectOptions = function(id) {
    return this._optionsMap[id];
}

ADAPTERJ.widget.SimpleListAdapter.prototype.bindViewHolder = function() {
    var entryIj = this._classId + "." + this._entryId;

    var element = document.getElementById(entryIj);
    var parent  = element.parentElement;
    
    var idIj = this._id + "[" + this._indexId + "].";
    var indexIj = "[" + this._indexId + "]";

    for (var i = 0; i < this._data.length; i ++) {
        var valueMap = { };
        var pojo = this._data[i];
        
        const nameArray = Object.keys(pojo);
        for (var j = 0; j < nameArray.length; j ++) {
            var name = nameArray[j];
            var id = idIj + name;
            var value = pojo[name];
            
            var options = this.getSelectOptions(name);
            if (options) {
                value = options.selected(value);
            }
            
            valueMap[id] = value;
        }
        
        var links = this._linksArray[i];
        if (links) {
            for (var j = 0; j < links.length(); j ++) {
                var link = links.getLink(j);
                if (link) {
                    var id = link.getId() + indexIj + "[" + j + "]";
                    
                    valueMap[id + ".text"] = link.getText();
                    valueMap[id + ".url"] = link.getURL();
                }
            }
        }
        
        const idArray = Object.keys(valueMap);
        for (var j = 0; j < idArray.length; j ++) {
            var id = idArray[j];
            var childElement = document.getElementById(id);
            if (childElement) {
                if (childElement.tagName == "A") {
                    childElement.href = valueMap[id];
                    if (childElement.innerHTML == "") childElement.innerHTML = valueMap[id];
                } else if (element.tagName == "IMG") {
                    childElement.src = valueMap[id];
                } else if (element.tagName == "INPUT") {
                    childElement.value = valueMap[id];
                } else {
                    childElement.innerHTML = valueMap[id];
                }
            }
        }

        var wrap1 = document.createElement("div");
        wrap1.appendChild(element.cloneNode(true));
        
        var html = wrap1.innerHTML;
        var index01 = "[" + i + "]";
/////// html = html.replaceAll(entryIj, entryIj + index01);
        html = html.replaceAll(indexIj, index01);
        
        var wrap2 = document.createElement("div");
        wrap2.innerHTML = html;
        
        var brother = wrap2.firstChild;
        parent.appendChild(brother);
    }
    
    parent.removeChild(element);
    element = null;
};

ADAPTERJ.widget.SimpleListAdapter.prototype.toString = function() {
    return "SimpleListAdapter{ id:" + this._id + " }";
}

/**
 * MapAdapter
 */

/**
 * SimpleMapAdapter
 */

