<#-- @ftlvariable name="comments" type="ru.itmo.wp.domain.Comment[]" -->
<#-- @ftlvariable name="comment" type="ru.itmo.wp.domain.Comment" -->

<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=postPage/>

    <#if user??>
        <form method="post" action="">
            <input type="hidden" name="action" value="postComment"/>
            <div class="field">
                <div class="name">
                    <label for="text">Comment</label>
                </div>
                <div class="value">
                    <textarea id="text" name="text">${comment.text!}</textarea>

                </div>
                <@c.error "comment.text"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Write">
            </div>
        </form>
    </#if>


    <#list postPage.comments as comment>
        <@c.comment comment=comment/>
    </#list>
</@c.page>