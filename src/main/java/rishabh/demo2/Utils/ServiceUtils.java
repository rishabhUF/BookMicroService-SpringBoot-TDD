package rishabh.demo2.Utils;


import org.apache.commons.lang3.StringUtils;

public class ServiceUtils {

    public String normalizeAuthorName(String author)
    {
        String authorName = StringUtils.normalizeSpace(author);
        return isSingleWord(author) ? authorName : splitOnFirstAndLastName(authorName);
    }

    private boolean isSingleWord(String correctAuthor)
    {
        return StringUtils.containsWhitespace(correctAuthor);
    }

    private String splitOnFirstAndLastName(String authorName)
    {
        String[] parts = StringUtils.splitByCharacterTypeCamelCase(authorName);
        String firstName = parts[0];
        if(parts.length == 1)
            return firstName;
        String lastName = StringUtils.substringAfterLast(authorName,firstName);
        return String.join(" ", firstName,lastName);
    }
}
